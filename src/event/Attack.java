package event;

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
    public Player defender;
    public String location;


    public Attack(Army attackingArmy, Player defender, String location) {
        this.attackingArmy = attackingArmy;
        this.defender = defender;
        this.location = location;

        String oldLocation = attackingArmy.getLocation();
        attackingArmy.player.resetArmy(oldLocation);
        attackingArmy.setLocation("outside");
        attackingArmy.player.attacks.add(this);
    }

    public Attack fight() {
        Player winner;
        switch (location) {
            case "hunting field":
                fightIn("hunting field", defender.hfArmy);
                break;
            case "dome":
                winner = fightIn("hunting field", defender.hfArmy);
                if (!winner.name.equals(defender.name))
                    fightIn("dome", defender.domeArmy);
                break;
            case "nest":
                winner = fightIn("hunting field", defender.hfArmy);
                if (!winner.name.equals(defender.name))
                    winner = fightIn("dome", defender.domeArmy);
                if (!winner.name.equals(defender.name))
                    fightIn("nest", defender.nestArmy);
                break;
        }
        if (attackingArmy.totalUnitAmount > 0)
            attackingArmy.player.domeArmy.addToArmy(attackingArmy);
        return this;
    }

    private Player fightIn(String location, Army defendingArmy) {

        if (defendingArmy.totalUnitAmount == 0) {
            System.out.println("- The defender had no army in his " + location + ".");
            return attackingArmy.player;
        }

        long attackingArmyXpValue = XpSystem.calculateArmyXpValue(attackingArmy);
        long defendingArmyXpValue = XpSystem.calculateArmyXpValue(defendingArmy);

        String header = getAttackerDefenderStatsTable(attackingArmy.player, defendingArmy.player, location);

        StringBuilder report = new StringBuilder("\nAttacking troops: " + attackingArmy + "\n"
                + "Defending troops: " + defendingArmy + "\n\n");

        Player attacker = attackingArmy.player; Player defender = defendingArmy.player;
        Player winner = null;
        boolean bothArmiesAreDead = false;
        boolean firstTurn = true;
        while (winner == null && !bothArmiesAreDead) {

            // Stats before the attacker/defender turns
            double defenseDamages;
            if (firstTurn) {
                defenseDamages = defendingArmy.defense * getOSReplicaFactor(defendingArmy);
                firstTurn = false;
            }
            else {
                defenseDamages = defendingArmy.defense;
            }

            // Attacker's turn
            report.append(attack1TurnIn(location, "attacker's turn", attackingArmy, defendingArmy, 0));

            // Defender's turn
            report.append(attack1TurnIn("hunting field", "defender's turn", defendingArmy, attackingArmy, defenseDamages));

            // Win conditions check
            if (attackingArmy.totalUnitAmount == 0) {
                winner = defender;
            }
            if (defendingArmy.totalUnitAmount == 0) {
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
        String xpResults = "", beforeAfterXp = "";
        if (bothArmiesAreDead) {
            winnerText += "\nBoth armies crashed into each other. No winner this time.\n";
        }
        else {
            winnerText += winner.name + " crushed the opposite army.\n";
            String attack, defense, hp = "";
            if (attackingArmy.totalUnitAmount != 0) {
                winnerText += "Surviving troops: " + attackingArmy + "\n";
                hp = "HF HP = " + StringFormatter.bigNumber(Math.round(attackingArmy.hp));
                attack = "Attack = " + StringFormatter.bigNumber(Math.round(attackingArmy.attack));
                defense = "Defense = " + StringFormatter.bigNumber(Math.round(attackingArmy.defense));
            }
            else {
                winnerText += "Surviving troops: " + defendingArmy + "\n";
                hp = "HF HP = " + StringFormatter.bigNumber(Math.round(defendingArmy.hp));
                attack = "Attack = " + StringFormatter.bigNumber(Math.round(defendingArmy.attack));
                defense = "Defense = " + StringFormatter.bigNumber(Math.round(defendingArmy.defense));
            }

            xpResults = XpSystem.calculateTroopsXp(attackingArmyXpValue, defendingArmyXpValue, attackingArmy,
                    defendingArmy,winner, location);

            beforeAfterXp = "\nStats: before -> after xp\n";
            if (attackingArmy.totalUnitAmount != 0) {
                hp += " -> " + StringFormatter.bigNumber(Math.round(attackingArmy.hp)) + "\n";
                attack += " -> " + StringFormatter.bigNumber(Math.round(attackingArmy.attack)) + "\n";
                defense += " -> " + StringFormatter.bigNumber(Math.round(attackingArmy.defense)) + "\n";
            }
            else {
                hp += " -> " + StringFormatter.bigNumber(Math.round(defendingArmy.hp)) + "\n";
                attack += " -> " + StringFormatter.bigNumber(Math.round(defendingArmy.attack)) + "\n";
                defense += " -> " + StringFormatter.bigNumber(Math.round(defendingArmy.defense)) + "\n";
            }
            beforeAfterXp += hp + attack + defense;
        }


        String footer = "\n################################################################################\n\n";

        System.out.println(header + report + winnerText + xpResults + beforeAfterXp + footer);
        return winner;
    }

    private static String attack1TurnIn(String location, String situation, Army attackingArmy, Army defendingArmy, double overrideDamages) {
        double damages = -1;
        if (overrideDamages > 0) {
            damages = overrideDamages;
        }
        else if (situation.equals("attacker's turn")) {
            damages = attackingArmy.attack;
        }
        else if (situation.equals("defender's turn")) {
            damages = attackingArmy.defense;
        }
        if (damages == -1)
            return "ERROR damages -1\n";

        String result = attackingArmy.player.name + " inflicts " + StringFormatter.bigNumber(Math.round(damages)) + " damages and kills ";

        double defenderHpMultiplier;
        if (situation.equals("defender's turn")) {
            defenderHpMultiplier = defendingArmy.player.hpMultiplier;
        }
        else {
            defenderHpMultiplier = defendingArmy.player.getHpMultiplierFor(location);
        }

        long kills = 0;
        if (damages > defendingArmy.hp) {
            kills = defendingArmy.totalUnitAmount;
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

    private String getAttackerDefenderStatsTable(Player attacker, Player defender, String location) {
        String header = "\n################################################################################\n"
                + "----------------------------     COMBAT  REPORT     ----------------------------\n\n"
                + Date.getRealDateAndTime() + " - " + attacker.name + " attacks " + defender.name + "'s " + location + ".\n\n";

        String defenderHpText = "";
        double defenderHpMultiplier = defender.getHpMultiplierFor(location);
        String defenderHpPercentage = (int) Math.round(defenderHpMultiplier * 100) + "%";
        switch (location) {
            case "hunting field": defenderHpText = "HP bonus (HF):"; break;
            case "dome": defenderHpText = "HP bonus (Dome):"; break;
            case "nest": defenderHpText = "HP bonus (Nest):"; break;
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

    public double getOSReplicaFactor(Army defendingArmy) {
        double attack = attackingArmy.attack;
        double defenderBaseHp = defendingArmy.hp / defendingArmy.player.hpMultiplier;

        if (attack < defendingArmy.hp) return 1;
        if (attack > 3 * defenderBaseHp) return 0.1;
        if (attack > 2 * defenderBaseHp) return 0.3;
        if (attack > 1.5 * defenderBaseHp) return 0.5;
        return 1;
    }
}
