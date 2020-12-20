package model;

import javafx.util.Pair;
import main.Config;
import utilities.StringFormatter;

import java.util.ArrayList;

public class Army extends ArrayList<Pair<Unit, Long>> {

    public Player player;

    public long totalAmount;
    public double attack;
    public double defense;
    public double hfHp;
    public double domeHp;
    public double nestHp;


    public Army() {
        for (Unit unit : Config.units) {
            add(unit);
        }
    }

    public Army(Player player) {
        this.player = player;
        for (Unit unit : Config.units) {
            add(unit);
        }
    }

    public Pair<Unit, Long> get(String unitName) {
        for (Pair<Unit, Long> pair : this) {
            if (pair.getKey().name.equals(unitName)) {
                return pair;
            }
        }
        return null;
    }

    public Unit getUnit(String unitName) {
        for (Pair<Unit, Long> pair : this) {
            if (pair.getKey().name.equals(unitName)) {
                return pair.getKey();
            }
        }
        return null;
    }

    public void set(String unitName, Pair<Unit, Long> element) {
        for (int index = 0; index < size(); index++) {
            Pair<Unit, Long> pair = get(index);
            if (pair.getKey().name.equals(unitName)) {
                set(index, element);
                return;
            }
        }
    }

    public void add(Unit unit) {
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
        if (player != null) {
            totalAmount += amount;
            attack += (player.attackMultiplier * unit.attack * amount);
            defense += (player.defenseMultiplier * unit.defense * amount);
            hfHp += (player.hpMultiplier * unit.hp * amount);
            domeHp += ((player.hpMultiplier + player.domeHpMultiplier - 1) * unit.hp * amount);
            nestHp += ((player.hpMultiplier + player.nestHpMultiplier - 1) * unit.hp * amount);
        }
    }

    private void removeStats(Unit unit, long amount) {
        if (player != null) {
            totalAmount -= amount;
            attack -= (player.attackMultiplier * unit.attack * amount);
            defense -= (player.defenseMultiplier * unit.defense * amount);
            hfHp -= (player.hpMultiplier * unit.hp * amount);
            domeHp -= ((player.hpMultiplier + player.domeHpMultiplier - 1) * unit.hp * amount);
            nestHp -= ((player.hpMultiplier + player.nestHpMultiplier - 1) * unit.hp * amount);
        }
    }

    public void recalculateAllStats() {
        totalAmount = 0;
        attack = defense = hfHp = domeHp = nestHp = 0;
        for (Pair<Unit, Long> pair : this) {
            addStats(pair.getKey(), pair.getValue());
        }
    }

    public void killAll() {
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

    public double getLocationHp(String location) {
        switch (location) {
            case "hunting field": return hfHp;
            case "dome": return domeHp;
            case "nest": return nestHp;
            default: return 0;
        }
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
