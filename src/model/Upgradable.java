package model;

public class Upgradable extends GameObject {

    public int level;
    public int ongoingUpgrades;
    public Player associatedPlayer;

    public double constructionTimePerLevelMultiplier;
    public double costPerLevelMultiplier;
    public double productionPerLevelMultiplier;
    public double storagePerLevelMultiplier;

    public long constructionTime;
    public long foodCost;
    public long woodCost;
    public long workerCost;
    public long foodProduction;
    public long woodProduction;
    public long foodStorage;
    public long woodStorage;


    public boolean levelUp() {
        if (playerHasEnoughResources()) {
            launchLevelUp();
            recalculateUpgradableStats(level + ongoingUpgrades);
        }
        return false;
    }

    public boolean playerHasEnoughResources() {
        return associatedPlayer.hasEnoughResource("food", foodCost)
                && associatedPlayer.hasEnoughResource("wood", woodCost)
                && associatedPlayer.hasEnoughResource("worker", workerCost);
    }

    public void launchLevelUp() {
        ongoingUpgrades++;
    }

    public void cancelLevelUp() {

    }

    public void recalculateUpgradableStats(int level) {
        constructionTime = Math.round(constructionTime * Math.pow(constructionTimePerLevelMultiplier, level));
        foodCost = Math.round(foodCost * Math.pow(costPerLevelMultiplier, level));
        woodCost = Math.round(woodCost * Math.pow(costPerLevelMultiplier, level));
        workerCost = Math.round(workerCost * Math.pow(costPerLevelMultiplier, level));
        foodProduction = Math.round(foodProduction * Math.pow(productionPerLevelMultiplier, level));
        woodProduction = Math.round(woodProduction * Math.pow(productionPerLevelMultiplier, level));
        foodStorage = Math.round(foodStorage * Math.pow(storagePerLevelMultiplier, level));
        woodStorage = Math.round(woodStorage * Math.pow(storagePerLevelMultiplier, level));
    }

    public void finishLevelUp() {

    }

    public void recalculatePlayerStats() {

    }
}
