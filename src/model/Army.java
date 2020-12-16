package model;

import javafx.util.Pair;

import java.util.ArrayList;

public class Army extends ArrayList<Pair<Unit, Integer>> {

    public Player player;

    @Override
    public String toString() {
        String toReturn = player.name + "'s army:";
        for (Pair<Unit, Integer> pair : this) {
            toReturn += "\n\t" + pair.getValue() + " " + pair.getKey().pluralName;
        }
        return toReturn;
    }
}
