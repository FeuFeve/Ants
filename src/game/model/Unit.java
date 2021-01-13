package game.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Unit extends GameObject {

    public String pluralName;
    public boolean isLayable;

    // Base stats
    public int hp;
    public int attack;
    public int defense;
    public int layingTime;
    public int foodCost;

    public String xpInto;

    // Hidden stats
    public int xpValue;


    public List<String> getBaseStatsList() {
        return Arrays.asList(name,
                String.valueOf(hp),
                String.valueOf(attack),
                String.valueOf(defense),
                layingTime + "s",
                String.valueOf(foodCost)
        );
    }

    public List<String> getBonusStatsList(Player player) {
        return Arrays.asList(name,
                String.valueOf(hp * player.hpMultiplier),
                String.valueOf(attack * player.attackMultiplier),
                String.valueOf(defense * player.defenseMultiplier),
                getBonusLayingTime(player) + "s",
                String.valueOf(foodCost)
        );
    }

    private String getBonusLayingTime(Player player) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en", "US"));
        double value = layingTime * player.layingSpeedMultiplier;
        if (value > 10) decimalFormat.applyPattern("#.#");
        else if (value > 1) decimalFormat.applyPattern("#.##");
        else if (value > 0.1) decimalFormat.applyPattern("#.###");
        else if (value > 0.01) decimalFormat.applyPattern("#.####");
        else decimalFormat.applyPattern("#.#####");
        return decimalFormat.format(value);
    }

    @Override
    public String toString() {
        return "Unit { " +
                "name='" + name + '\'' +
                ", pluralName='" + pluralName +
                ", hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", layingTime=" + layingTime +
                ", foodCost=" + foodCost + '\'' +
                " }";
    }
}
