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

    public void recalculateLayingSpeedMultiplier() {
        layingSpeedMultiplier = Math.pow(0.9, layingSpeedLevel);
    }
}
