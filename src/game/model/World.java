package game.model;

import game.main.Config;
import utilities.FileManager;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class World {

    public static final String WORLDS_FOLDER_PATH = "Worlds/";

    public String name;

    public int startingPlayers;
    List<Player> players = new ArrayList<>();


    public World(String name) {
        this.name = name;
    }

    public World setStartingPlayers(int startingPlayers) {
        this.startingPlayers = startingPlayers;
        return this;
    }

    public World create() {
        if (startingPlayers == 0) {
            startingPlayers = 5;
        }
        for (int i = 0; i < startingPlayers; i++) {
            players.add(new Player("Player " + i));
        }
        return this;
    }

    public void save() {
        FileManager.saveToJson(this, WORLDS_FOLDER_PATH + name + "/save.json");
    }

    private static boolean hasSaveFile(File file) {
        List<String> content = Arrays.asList(file.list());
        return content.contains("save.json");
    }

    public static String[] getSavedWorlds() {
        File file = new File(WORLDS_FOLDER_PATH);
        return file.list((current, name) -> {
            File world = new File(current, name);
            return world.isDirectory() && hasSaveFile(world) && Config.hasValidConfig(world);
        });
    }
}
