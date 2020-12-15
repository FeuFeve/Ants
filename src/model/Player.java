package model;

public class Player extends GameObject {

    double hfHpMultiplier = 1;
    double domeHpMultiplier = 1;
    double nestHpMultiplier = 1;
    double attackMultiplier = 1;
    double defenseMultiplier = 1;
    double xpMultiplier = 1;

    int layingSpeedLevel;
    double layingSpeedMultiplier;


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
