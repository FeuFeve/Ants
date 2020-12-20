package model;

import javafx.util.Pair;
import main.Config;
import utilities.StringFormatter;

import java.util.ArrayList;

public class Army extends ArrayList<Pair<Unit, Long>> {

    // Player related
    public Player player;

    // Army related
    public long totalUnitAmount;
    public double attack;
    public double defense;
    public double hp;
    private String location;


    public Army() {
        for (Unit unit : Config.units) {
            add(unit);
        }
        this.location = "outside";
    }

    public Army(Player player) {
        this(player, "outside");
    }

    public Army(Player player, String location) {
        this.player = player;
        for (Unit unit : Config.units) {
            add(unit);
        }
        this.location = location;
        switch (location) {
            case "hunting field": player.hfArmy = this; break;
            case "dome": player.domeArmy = this; break;
            case "nest": player.nestArmy = this; break;
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
                removeStats(pair.getKey(), pair.getValue());
                set(index, element);
                addStats(element.getKey(), element.getValue());
                return;
            }
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
        totalUnitAmount += amount;
        if (player != null) {
            attack += (player.attackMultiplier * unit.attack * amount);
            defense += (player.defenseMultiplier * unit.defense * amount);
            double hpMultiplier = player.getHpMultiplierFor(location);
            hp += (hpMultiplier * unit.hp * amount);
        }
        else {
            attack += (unit.attack * amount);
            defense += (unit.defense * amount);
            hp += (unit.hp * amount);
        }
    }

    private void removeStats(Unit unit, long amount) {
        totalUnitAmount -= amount;
        if (player != null) {
            attack -= (player.attackMultiplier * unit.attack * amount);
            defense -= (player.defenseMultiplier * unit.defense * amount);
            double hpMultiplier = player.getHpMultiplierFor(location);
            hp -= (hpMultiplier * unit.hp * amount);
        }
        else {
            attack -= (unit.attack * amount);
            defense -= (unit.defense * amount);
            hp -= (unit.hp * amount);
        }
    }

    public void recalculateAllStats() {
        totalUnitAmount = 0;
        attack = defense = hp = 0;
        for (Pair<Unit, Long> pair : this) {
            addStats(pair.getKey(), pair.getValue());
        }
    }

    public void killAll() {
        this.clear();
        for (Unit unit : Config.units) {
            add(unit);
        }
        totalUnitAmount = 0;
        attack = 0;
        defense = 0;
        hp = 0;
    }

    public void addToArmy(Army armyToAdd) {
        for (Pair<Unit, Long> pairToAdd : armyToAdd) {
            add(pairToAdd.getKey().name, pairToAdd.getValue());
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String newLocation) {
        location = newLocation;
        recalculateAllStats();
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
        if (toReturn.toString().isEmpty())
            return "None";
        else
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
        if (toReturn.toString().isEmpty())
            return "None";
        else
            return toReturn + ".";
    }
}
