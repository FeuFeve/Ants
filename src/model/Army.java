package model;

import javafx.util.Pair;

import java.util.ArrayList;

public class Army extends ArrayList<Pair<Unit, Integer>> {

    public Player player;

    @Override
    public String toString() {
        String toReturn = "Army {\n\tPlayer: " + player.name + "\n";
        for (Pair<Unit, Integer> pair : this) {
            toReturn += "\t" + pair.getValue() + " " + pair.getKey().pluralName + "\n";
        }
        toReturn += "}";
        return toReturn;
    }
}
