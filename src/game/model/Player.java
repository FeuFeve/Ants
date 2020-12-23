package game.model;

import game.event.Attack;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {

    public boolean isHuman;

    // General stats
    public int layingSpeedLevel;
    public double layingSpeedMultiplier;

    // Resources
    public long workers;
    public long food;
    public long wood;
    public long maxFood = 1700;
    public long maxWood = 1700;

    // Army related
    public double attackMultiplier = 1;
    public double defenseMultiplier = 1;
    public double hpMultiplier = 1;
    public double domeHpMultiplier = 1;
    public double nestHpMultiplier = 1;
    public double xpMultiplier = 1;

    // Armies
    public Army hfArmy = new Army(this);
    public Army domeArmy = new Army(this);
    public Army nestArmy = new Army(this);

    // Events
    public List<Attack> attacks = new ArrayList<>();


    public Player() {
        recalculateLayingSpeedMultiplier();
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    public Player setHuman() {
        isHuman = true;
        return this;
    }

    public void addResource(String name, long amount) {
        switch (name) {
            case "food":
                food += amount;
                if (food > maxFood)
                    food = maxFood;
                break;
            case "wood":
                wood += amount;
                if (wood > maxWood)
                    wood = maxWood;
                break;
            case "worker":
                workers += amount;
                break;
        }
    }

    public boolean hasEnoughResource(String name, long amount) {
        switch (name) {
            case "food":
                return food >= amount;
            case "wood":
                return wood >= amount;
            case "worker":
                return workers >= amount;
        }
        return false;
    }

    public boolean removeResource(String name, long amount) {
        if (hasEnoughResource(name, amount)) {
            switch (name) {
                case "food": food -= amount; return true;
                case "wood": wood -= amount; return true;
                case "worker": workers -= amount; return true;
            }
        }
        return false;
    }

    public void resetArmy(String location) {
        switch (location) {
            case "hunting field": hfArmy = new Army(this, location);
            case "dome": domeArmy = new Army(this, location);
            case "nest": nestArmy = new Army(this, location);
        }
    }

    public void debugModifyStats(int weapons, int shields, int dome, int nest, int mealybugs) {
        attackMultiplier = defenseMultiplier = Math.round((1 + 0.1 * weapons) * 100) / 100.0;
        hpMultiplier = Math.round((1 + 0.1 * shields) * 100) / 100.0;
        domeHpMultiplier = Math.round((1.1 + 0.05 * dome) * 100) / 100.0;
        nestHpMultiplier = Math.round((1.3 + 0.15 * nest) * 100) / 100.0;
        xpMultiplier = Math.round((1 +  0.1 * mealybugs) * 100) / 100.0;

        hfArmy.recalculateAllStats();
        domeArmy.recalculateAllStats();
        nestArmy.recalculateAllStats();
    }

    public void recalculateLayingSpeedMultiplier() {
        layingSpeedMultiplier = Math.pow(0.9, layingSpeedLevel);
    }

    public double getHpMultiplierFor(String location) {
        switch (location) {
            case "nest": return hpMultiplier + nestHpMultiplier - 1;
            case "dome": return hpMultiplier + domeHpMultiplier - 1;
            default: return hpMultiplier;
        }
    }

    public void displayArmies() {
        System.out.println("\n####################");
        System.out.println("--- " + name + " armies ---");
        System.out.println("# HF ARMY:");
        System.out.println(hfArmy.toString());
        System.out.println("# DOME ARMY:");
        System.out.println(domeArmy.toString());
        System.out.println("# NEST ARMY:");
        System.out.println(nestArmy.toString());
        System.out.println("####################\n");
    }
}
