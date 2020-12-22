package game.model;

public class Building extends Upgradable {

    public void levelUp(Player player) {
        long levelUpTimeCost;

        if (level == 0) {
            if (name.equals("Food warehouse")) { // TODO: check real name
                player.maxFood -= 1200;
                player.maxFood += foodStorage;
            }
            else if (name.equals("Wood warehouse")) { // TODO: check real name
                player.maxWood -= 1200;
                player.maxWood += woodStorage;
            }
        }
        else {
        }
    }

    @Override
    public String toString() {
        return name + ": level " + level;
    }
}
