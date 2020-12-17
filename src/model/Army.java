package model;

import javafx.util.Pair;
import main.Config;
import utilities.Date;
import utilities.StringFormatter;
import wagu.Block;
import wagu.Board;
import wagu.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Army extends ArrayList<Pair<Unit, Long>> {

    public Player player;

    public long totalAmount;
    public double attack;
    public double defense;
    public double hfHp;
    public double domeHp;
    public double nestHp;


    public Army(Player player) {
        this.player = player;
        for (Unit unit : Config.units) {
            add(unit);
        }
    }

    private void add(Unit unit) {
        super.add(new Pair<>(unit, 0L));
    }

    @Override
    public boolean add(Pair<Unit, Long> pair) {
        return add(pair.getKey().name, pair.getValue());
    }

    public boolean add(String unitName, long amount) {
        int index = 0;
        for (Pair<Unit, Long> pair : this) {
            if (pair.getKey().name.equals(unitName)) {
                set(index, new Pair<>(pair.getKey(), pair.getValue() + amount));
                addStats(pair.getKey(), amount);
                return true;
            }
            index++;
        }
        return false;
    }

    private void addStats(Unit unit, long amount) {
        totalAmount += amount;
        attack += (player.attackMultiplier * unit.attack * amount);
        defense += (player.defenseMultiplier * unit.defense * amount);
        hfHp += (player.hpMultiplier * unit.hp * amount);
        domeHp += ((player.hpMultiplier + player.domeHpMultiplier - 1) * unit.hp * amount);
        nestHp += ((player.hpMultiplier + player.nestHpMultiplier - 1) * unit.hp * amount);
    }

    private void removeStats(Unit unit, long amount) {
        totalAmount -= amount;
        attack -= (player.attackMultiplier * unit.attack * amount);
        defense -= (player.defenseMultiplier * unit.defense * amount);
        hfHp -= (player.hpMultiplier * unit.hp * amount);
        domeHp -= ((player.hpMultiplier + player.domeHpMultiplier - 1) * unit.hp * amount);
        nestHp -= ((player.hpMultiplier + player.nestHpMultiplier - 1) * unit.hp * amount);
    }

    private void recalculateAllStats() {
        totalAmount = 0;
        attack = defense = hfHp = domeHp = nestHp = 0;
        for (Pair<Unit, Long> pair : this) {
            addStats(pair.getKey(), pair.getValue());
        }
    }

    private void killAll() {
        this.clear();
        for (Unit unit : Config.units) {
            add(unit);
        }
        totalAmount = 0;
        attack = 0;
        defense = 0;
        hfHp = 0;
        domeHp = 0;
        nestHp = 0;
    }

    public static void attackIn(String location, Army attackingArmy, Army defendingArmy) {
        String header = getAttackerDefenderStatsTable(location, attackingArmy.player, defendingArmy.player);

        StringBuilder report = new StringBuilder("\nAttacking troops: " + attackingArmy + "\n"
                + "Defending troops: " + defendingArmy + "\n\n");

        Player attacker = attackingArmy.player; Player defender = defendingArmy.player;
        Player winner = null;
        boolean bothArmiesAreDead = false;
        boolean firstTurn = true;
        while (winner == null && !bothArmiesAreDead) {

            // Stats before the attacker/defender turns
            double defenseDamages = -1;
            if (firstTurn) {
                defenseDamages = defendingArmy.defense * getOSReplicaFactor(attackingArmy.attack, defendingArmy.hfHp / defendingArmy.player.hpMultiplier);
                firstTurn = false;
            }
            else {
                defenseDamages = defendingArmy.defense;
            }

            // Attacker's turn
            report.append(attack1TurnIn(location, "attack", attackingArmy, defendingArmy, 0));
//            report += "# " + attacker.name + ": " + attackingArmy + "\n";
//            report += "# " + defender.name + ": " + defendingArmy + "\n";

            // Defender's turn
            report.append(attack1TurnIn("hunting field", "defense", defendingArmy, attackingArmy, defenseDamages));
//            report += "# " + attacker.name + ": " + attackingArmy + "\n";
//            report += "# " + defender.name + ": " + defendingArmy + "\n";

            // Win conditions check
            if (attackingArmy.totalAmount == 0) {
                winner = defender;
            }
            if (defendingArmy.totalAmount == 0) {
                if (winner == null) {
                    winner = attacker;
                }
                else {
                    winner = null;
                    bothArmiesAreDead = true;
                }
            }

        }

        String winnerText = "\n";
        if (bothArmiesAreDead) {
            winnerText += "\nBoth armies crashed into each other. No winner this time.\n";
        }
        else {
            winnerText += winner.name + " crushed the opposite army.\n";
            if (attackingArmy.totalAmount != 0) {
                winnerText += "Surviving troops: " + attackingArmy + "\n";
                winnerText += "Stats: Attack = " + StringFormatter.bigNumber(Math.round(attackingArmy.attack))
                        + ", Defense = " + StringFormatter.bigNumber(Math.round(attackingArmy.defense))
                        + ", HF HP = " + StringFormatter.bigNumber(Math.round(attackingArmy.hfHp)) + "\n";
            }
            else {
                winnerText += "Surviving troops: " + defendingArmy + "\n";
                winnerText += "Stats: Attack = " + StringFormatter.bigNumber(Math.round(defendingArmy.attack))
                        + ", Defense = " + StringFormatter.bigNumber(Math.round(defendingArmy.defense))
                        + ", HF HP = " + StringFormatter.bigNumber(Math.round(defendingArmy.hfHp)) + "\n";
            }
        }

        String xpResults = "\nSurviving units learned from this battle:\n"
                + "- Not implemented yet.\n";

        String footer = "\n################################################################################\n\n";

        System.out.println(header + report + winnerText + xpResults + footer);
    }

    private static double getOSReplicaFactor(double attack, double defenderHp) {
        if (attack > 3 * defenderHp) return 0.1;
        if (attack > 2 * defenderHp) return 0.3;
        if (attack > 1.5 * defenderHp) return 0.5;
        return 1;
    }

    private static double getDefenderHp(String location, Army army) {
        switch (location) {
            case "hunting field": return army.hfHp;
            case "dome": return army.domeHp;
            case "nest": return army.nestHp;
            default: return 0;
        }
    }

    private static String attack1TurnIn(String location, String situation, Army attackingArmy, Army defendingArmy, double overrideDamages) {
        double damages = -1;
        if (overrideDamages > 0) {
            damages = overrideDamages;
        }
        else if (situation.equals("attack")) {
            damages = attackingArmy.attack;
        }
        else if (situation.equals("defense")) {
            damages = attackingArmy.defense;
        }
        if (damages == -1)
            return "ERROR damages -1\n";

        String result = attackingArmy.player.name + " inflicts " + StringFormatter.bigNumber(Math.round(damages)) + " damages and kills ";

        double defenderHp;
        double defenderHpMultiplier;
        if (situation.equals("defense")) {
            defenderHp = defendingArmy.hfHp;
            defenderHpMultiplier = defendingArmy.player.hpMultiplier;
        }
        else {
            defenderHp = getDefenderHp(location, defendingArmy);
            defenderHpMultiplier = defendingArmy.player.getHpMultiplierFor(location);
        }

        long kills = 0;
        if (damages > defenderHp) {
            kills = defendingArmy.totalAmount;
            defendingArmy.killAll();
        }
        else {
            int unitIndex = 0;
            for (Pair<Unit, Long> pair : defendingArmy) {
                Unit unit = pair.getKey();
                long amount = pair.getValue();
                if (amount == 0) {
                    unitIndex++;
                    continue;
                }

                double unitHp = unit.hp * defenderHpMultiplier;
                double unitCastHp = unitHp * amount;
                if (damages > unitCastHp) { // The entire cast died
                    kills += amount;
                    defendingArmy.set(unitIndex, new Pair<>(unit, 0L));
                    damages -= unitCastHp;
                }
                else { // Less damages than the cast's life
                    long survivingUnits = (long) Math.ceil((unitCastHp - damages) / unitHp);
                    kills += amount - survivingUnits;
                    defendingArmy.set(unitIndex, new Pair<>(unit, survivingUnits));
                    break;
                }
                unitIndex++;
            }
            defendingArmy.recalculateAllStats();
        }
        result += StringFormatter.bigNumber(kills) + " enemies.\n";
        return result;
    }

    private static String getAttackerDefenderStatsTable(String location, Player attacker, Player defender) {
        String header = "\n################################################################################\n"
                + "----------------------------     COMBAT  REPORT     ----------------------------\n\n"
                + Date.getRealDateAndTime() + " - " + attacker.name + " attacks " + defender.name + "'s " + location + ".\n\n";

        String defenderHpText = ""; String defenderHpPercentage = "";
        switch (location) {
            case "hunting field":
                defenderHpText = "HP bonus (HF):";
                defenderHpPercentage = (int) Math.round(defender.hpMultiplier * 100) + "%";
                break;
            case "dome":
                defenderHpText = "HP bonus (Dome):";
                defenderHpPercentage = (int) Math.round((defender.hpMultiplier + defender.domeHpMultiplier - 1) * 100) + "%";
                break;
            case "nest":
                defenderHpText = "HP bonus (Nest):";
                defenderHpPercentage = (int) Math.round((defender.hpMultiplier + defender.nestHpMultiplier - 1) * 100) + "%";
                break;
        }

        List<String> headersList = Arrays.asList("ATTACKER", "DEFENDER");
        List<List<String>> rowsList = Arrays.asList(
                Arrays.asList(attacker.name, defender.name),
                Arrays.asList(
                        StringFormatter.firstLeftSecondRightAlign(28, "Attack bonus:", (int) Math.round(attacker.attackMultiplier * 100) + "%", true),
                        StringFormatter.firstLeftSecondRightAlign(28, "Defense bonus:", (int) Math.round(defender.defenseMultiplier * 100) + "%", true)
                ),
                Arrays.asList(
                        StringFormatter.firstLeftSecondRightAlign(28, "HP bonus (HF):", (int) Math.round(attacker.hpMultiplier * 100) + "%", true),
                        StringFormatter.firstLeftSecondRightAlign(28, defenderHpText, defenderHpPercentage, true)
                )
        );

        Board board = new Board(63);
        Table table = new Table(board, 63, headersList, rowsList);
        List<Integer> colAlignList = Arrays.asList(Block.DATA_CENTER, Block.DATA_CENTER);
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();

        header += board.getPreview();
        return header;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        boolean first = true;
        for (Pair<Unit, Long> unit : this) {
            if (unit.getValue() > 0) {
                if (first) {
                    first = false;
                    if (unit.getValue() == 1)
                        toReturn.append(StringFormatter.bigNumber(unit.getValue())).append(" ").append(unit.getKey().name);
                    else
                        toReturn.append(StringFormatter.bigNumber(unit.getValue())).append(" ").append(unit.getKey().pluralName);
                } else {
                    if (unit.getValue() == 1)
                        toReturn.append(", ").append(StringFormatter.bigNumber(unit.getValue())).append(" ").append(unit.getKey().name);
                    else
                        toReturn.append(", ").append(StringFormatter.bigNumber(unit.getValue())).append(" ").append(unit.getKey().pluralName);
                }
            }
        }
        return toReturn + ".";
    }

    public String toStringComplete() {
        Pair<Unit, Long> unit = get(0);
        StringBuilder toReturn = new StringBuilder(StringFormatter.bigNumber(unit.getValue()) + " " + unit.getKey().pluralName);
        for (int i = 1; i < size(); i++) {
            unit = get(i);
            if (unit.getValue() <= 1)
                toReturn.append(", ").append(StringFormatter.bigNumber(unit.getValue())).append(" ").append(unit.getKey().name);
            else
                toReturn.append(", ").append(StringFormatter.bigNumber(unit.getValue())).append(" ").append(unit.getKey().pluralName);
        }
        return toReturn + ".";
    }
}
