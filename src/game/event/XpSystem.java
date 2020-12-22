package game.event;

import javafx.util.Pair;
import game.model.Army;
import game.model.Player;
import game.model.Unit;
import utilities.StringFormatter;

public class XpSystem {

    public static double xpDropMultiplier = 0.66;


    public static long calculateArmyXpValue(Army army) {
        long armyXpValue = 0;
        for (Pair<Unit, Long> pair : army) {
            Unit unit = pair.getKey();
            long amount = pair.getValue();
            if (amount > 0) {
                armyXpValue += unit.xpValue * amount;
            }
        }
        return armyXpValue;
    }

    public static String calculateTroopsXp(long attackingArmyXpValue, long defendingArmyXpValue, Army attackingArmy,
                                           Army defendingArmy, Player winner, String location) {

        long winnerArmyXpValue, loserArmyXpValue;
        Army winnerArmy, loserArmy;
        if (attackingArmy.player.name.equals(winner.name)) {
            winnerArmyXpValue = attackingArmyXpValue;
            winnerArmy = attackingArmy;
            loserArmyXpValue = defendingArmyXpValue;
            loserArmy = defendingArmy;
        }
        else {
            winnerArmyXpValue = defendingArmyXpValue;
            winnerArmy = defendingArmy;
            loserArmyXpValue = attackingArmyXpValue;
            loserArmy = attackingArmy;
        }

        long loserArmyDroppedXpValue = Math.round(loserArmyXpValue * xpDropMultiplier);

        double ratioWinLose = (double) winnerArmyXpValue / (double) loserArmyDroppedXpValue;
        double baseXpRatio = 1 / Math.pow(ratioWinLose, 2);

        double winnerAttackFactor = winnerArmy.player.attackMultiplier;
        double loserAttackFactor = loserArmy.player.attackMultiplier;
        double winnerLifeFactor, loserLifeFactor, winnerXpFactor;
        if (attackingArmy.player.name.equals(winner.name)) {
            winnerLifeFactor = attackingArmy.player.hpMultiplier;
            if (location.equals("hunting field"))
                loserLifeFactor = defendingArmy.player.hpMultiplier;
            else if (location.equals("dome"))
                loserLifeFactor = defendingArmy.player.domeHpMultiplier + defendingArmy.player.hpMultiplier - 1;
            else
                loserLifeFactor = defendingArmy.player.nestHpMultiplier + defendingArmy.player.hpMultiplier - 1;
            winnerXpFactor = attackingArmy.player.xpMultiplier;
        }
        else {
            loserLifeFactor = attackingArmy.player.hpMultiplier;
            if (location.equals("hunting field"))
                winnerLifeFactor = defendingArmy.player.hpMultiplier;
            else if (location.equals("dome"))
                winnerLifeFactor = defendingArmy.player.domeHpMultiplier + defendingArmy.player.hpMultiplier - 1;
            else
                winnerLifeFactor = defendingArmy.player.nestHpMultiplier + defendingArmy.player.hpMultiplier - 1;
            winnerXpFactor = defendingArmy.player.xpMultiplier;
        }

        double bonusXpRatio = baseXpRatio / winnerAttackFactor / winnerLifeFactor * loserAttackFactor * loserLifeFactor * winnerXpFactor;

        return xpArmy(winnerArmy, bonusXpRatio);
    }

    public static String xpArmy(Army army, double xpRatio) {
        if (xpRatio > 1)
            xpRatio = 1;

        StringBuilder xpResults = new StringBuilder();

        // Calculate the amounts of units that level-up
        Army xpTroops = new Army();
        for (int index = 0; index < army.size(); index++) {
            Pair<Unit, Long> pair = army.get(index);
            Unit unit = pair.getKey();
            long amount = pair.getValue();
            if (unit.xpInto != null && !unit.xpInto.isEmpty() && amount > 0) {
                long levelUppedAmount = (long) Math.floor(amount * xpRatio);

                // Remove the levelUppedAmount from the current caste
                army.set(index, new Pair<>(unit, amount - levelUppedAmount));

                // Add to the xpTroops the units that level-upped
                Pair<Unit, Long> existingPair = xpTroops.get(unit.xpInto);
                xpTroops.add(unit.xpInto, levelUppedAmount);

                xpResults.append("- ").append(StringFormatter.bigNumber(levelUppedAmount)).append(" ").append(unit.pluralName).append(" became ")
                        .append(existingPair.getKey().pluralName).append("\n");
            }
        }

        // Add the xpTroops to the army
        for (int index = 0; index < army.size(); index++) {
            Pair<Unit, Long> xpTroopsPair = xpTroops.get(index);

            long xpTroopsUnitAmount = xpTroopsPair.getValue();
            if (xpTroopsUnitAmount > 0) {
                Pair<Unit, Long> armyPair = army.get(index);
                Unit armyUnit = armyPair.getKey();
                long armyUnitAmount = armyPair.getValue();

                army.set(index, new Pair<>(armyUnit, armyUnitAmount + xpTroopsUnitAmount));
            }
        }

        String result = "";
        if (!xpResults.toString().isEmpty()) {
            result = "\nSurviving units learned from this battle:\n";
            result += xpResults.toString();
            result += "XP rate = " + (Math.round(xpRatio * 10_000) / 100.0) + "%\n";
        }

        army.recalculateAllStats();
        return result;
    }
}
