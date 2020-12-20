package model;

public class Building extends GameObject {

    public int level;

    public double constructionTimePerLevelMultiplier;
    public double costPerLevelMultiplier;
    public double productionPerLevelMultiplier;
    public double storagePerLevelMultiplier;

    public long constructionTime;
    public long foodCost;
    public long woodCost;
    public long foodProduction;
    public long woodProduction;
    public long foodStorage;
    public long woodStorage;


    @Override
    public String toString() {
        return name + ": level " + level;
    }
}
