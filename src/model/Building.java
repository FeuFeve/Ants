package model;

public class Building extends GameObject {

    public int level;

    public double costMultiplierPerLevel = 2;
    public double productionMultiplierPerLevel;
    public double storageMultiplierPerLevel = 2;

    public long woodCost;
    public long foodCost;
    public long woodProduction;
    public long foodProduction;
    public long woodStorage;
    public long foodStorage;
}
