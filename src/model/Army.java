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

public class Army extends ArrayList<Pair<Unit, Integer>> {

    public Player player;

    int totalAmount;
    double attack;
    double defense;
    double hfHp;
    double domeHp;
    double nestHp;


    public Army() {
        for (Unit unit : Config.units) {
            add(unit);
        }
    }

    private void add(Unit unit) {
        super.add(new Pair<>(unit, 0));
    }

    @Override
    public boolean add(Pair<Unit, Integer> pair) {
        return add(pair.getKey().name, pair.getValue());
    }

    public boolean add(String unitName, int amount) {
        int index = 0;
        for (Pair<Unit, Integer> pair : this) {
            if (pair.getKey().name.equals(unitName)) {
                set(index, new Pair<>(pair.getKey(), pair.getValue() + amount));
                addStats(pair.getKey(), amount);
                return true;
            }
            index++;
        }
        return false;
    }

    private void addStats(Unit unit, int amount) {
        totalAmount += amount;
        attack += (player.attackMultiplier * unit.attack * amount);
        defense += (player.defenseMultiplier * unit.defense * amount);
        hfHp += (player.hpMultiplier * unit.hp * amount);
        domeHp += ((player.hpMultiplier + player.domeHpMultiplier - 1) * unit.hp * amount);
        nestHp += ((player.hpMultiplier + player.nestHpMultiplier - 1) * unit.hp * amount);
    }

    private void removeStats(Unit unit, int amount) {
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
        for (Pair<Unit, Integer> pair : this) {
            addStats(pair.getKey(), pair.getValue());
        }
    }

    public void attackInHf(Army enemyArmy) {
        System.out.println(getAttackerDefenderStatsTable("hunting field", player, enemyArmy.player));
    }

    public void attackInDome(Army enemyArmy) {
        System.out.println(getAttackerDefenderStatsTable("dome", player, enemyArmy.player));
    }

    public void attackInNest(Army enemyArmy) {
        System.out.println(getAttackerDefenderStatsTable("nest", player, enemyArmy.player));
    }

    private static String getAttackerDefenderStatsTable(String location, Player attacker, Player defender) {
        String header = Date.getRealDate() + "\n"
                + attacker.name + " attacks " + defender.name + "'s " + location + ".\n\n";

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
        String toReturn = "";
        boolean first = true;
        for (Pair<Unit, Integer> unit : this) {
            if (unit.getValue() > 0) {
                if (first) {
                    first = false;
                    toReturn += unit.getValue() + " " + unit.getKey().name;
                } else {
                    toReturn += ", " + unit.getValue() + " " + unit.getKey().name;
                }
            }
        }
        return toReturn + ".";
    }

    public String toStringComplete() {
        Pair<Unit, Integer> unit = get(0);
        String toReturn = unit.getValue() + " " + unit.getKey().name;
        for (int i = 1; i < size(); i++) {
            unit = get(i);
            toReturn += ", " + unit.getValue() + " " + unit.getKey().name;
        }
        return toReturn + ".";
    }
}
