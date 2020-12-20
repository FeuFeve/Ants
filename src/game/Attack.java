package game;

import javafx.util.Pair;
import model.Army;
import model.Player;
import model.Unit;
import utilities.Date;
import utilities.StringFormatter;
import wagu.Block;
import wagu.Board;
import wagu.Table;

import java.util.Arrays;
import java.util.List;

public class Attack extends GameEvent {

    public Army attackingArmy;
    public Army defendingArmy;
    public String location;


    public Attack(Army attackingArmy, Army defendingArmy, String location) {
        this.attackingArmy = attackingArmy;
        this.defendingArmy = defendingArmy;
        this.location = location;
    }

    public void fight() {

        long attackingArmyXpValue = XpSystem.calculateArmyXpValue(attackingArmy);
        long defendingArmyXpValue = XpSystem.calculateArmyXpValue(defendingArmy);

        String header = getAttackerDefenderStatsTable(attackingArmy.player, defendingArmy.player);

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
                defenseDamages = defendingArmy.defense * getOSReplicaFactor();
                firstTurn = false;
            }
            else {
                defenseDamages = defendingArmy.defense;
            }

            // Attacker's turn
            report.append(attack1TurnIn(location, "attack", attackingArmy, defendingArmy, 0));

            // Defender's turn
            report.append(attack1TurnIn("hunting field", "defense", defendingArmy, attackingArmy, defenseDamages));

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

        String xpResults = "";
        if (!bothArmiesAreDead) {
            xpResults = XpSystem.calculateTroopsXp(attackingArmyXpValue, defendingArmyXpValue,
                    attackingArmy, defendingArmy,winner, location);
        }

        String footer = "\n################################################################################\n\n";

        System.out.println(header + report + winnerText + xpResults + footer);
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
            defenderHp = defendingArmy.getLocationHp(location);
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

    private String getAttackerDefenderStatsTable(Player attacker, Player defender) {
        String header = "\n################################################################################\n"
                + "----------------------------     COMBAT  REPORT     ----------------------------\n\n"
                + Date.getRealDateAndTime() + " - " + attacker.name + " attacks " + defender.name + "'s " + location + ".\n\n";

        String defenderHpText = "";
        String defenderHpPercentage = "";
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
                        StringFormatter.firstLeftSecondRightAlign(28, "Attack bonus:",
                                (int) Math.round(attacker.attackMultiplier * 100) + "%", true),
                        StringFormatter.firstLeftSecondRightAlign(28, "Defense bonus:",
                                (int) Math.round(defender.defenseMultiplier * 100) + "%", true)
                ),
                Arrays.asList(
                        StringFormatter.firstLeftSecondRightAlign(28, "HP bonus (HF):",
                                (int) Math.round(attacker.hpMultiplier * 100) + "%", true),
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

    public double getOSReplicaFactor() {
        double attack = attackingArmy.attack;
        double defenderRealHp = defendingArmy.getLocationHp(location);
        double defenderBaseHp = defendingArmy.hfHp / defendingArmy.player.hpMultiplier;

        if (attack < defenderRealHp) return 1;
        if (attack > 3 * defenderBaseHp) return 0.1;
        if (attack > 2 * defenderBaseHp) return 0.3;
        if (attack > 1.5 * defenderBaseHp) return 0.5;
        return 1;
    }
}
