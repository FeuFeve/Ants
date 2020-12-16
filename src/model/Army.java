package model;

import javafx.util.Pair;
import main.Config;
import utilities.Date;

import java.util.ArrayList;

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
        Date.getRealDate();
    }

    @Override
    public String toString() {
        Pair<Unit, Integer> unit = get(0);
        String toReturn = unit.getValue() + " " + unit.getKey().name;
        for (int i = 1; i < size(); i++) {
            unit = get(i);
            toReturn += ", " + unit.getValue() + " " + unit.getKey().name;
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
