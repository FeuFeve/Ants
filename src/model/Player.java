package model;

public class Player extends GameObject {

    public double attackMultiplier = 1;
    public double defenseMultiplier = 1;
    public double hpMultiplier = 1;
    public double domeHpMultiplier = 1;
    public double nestHpMultiplier = 1;
    public double xpMultiplier = 1;

    public int layingSpeedLevel;
    public double layingSpeedMultiplier;


    public Player() {
        recalculateLayingSpeedMultiplier();
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    public void debugModifyStats(int weapons, int shields, int dome, int nest) {
        attackMultiplier = defenseMultiplier = 1 + weapons * 0.1;
        hpMultiplier = 1 + shields * 0.1;
        domeHpMultiplier = 1.1 + 0.05 * dome;
        nestHpMultiplier = 1.3 + 0.15 * nest;
    }

    public void recalculateLayingSpeedMultiplier() {
        layingSpeedMultiplier = Math.pow(0.9, layingSpeedLevel);
    }

    public double getHpMultiplierFor(String location) {
        switch (location) {
            case "hunting field": return hpMultiplier;
            case "dome": return hpMultiplier + domeHpMultiplier - 1;
            case "nest": return hpMultiplier + nestHpMultiplier - 1;
            default: return 0;
        }
    }
}
