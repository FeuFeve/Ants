package game.model;

import game.main.Config;

import java.io.File;
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
        Config.loadConfigFor(name);
    }

    public World setStartingPlayers(int startingPlayers) {
        this.startingPlayers = startingPlayers;
        return this;
    }

    public World build() {
        if (startingPlayers == 0) {
            startingPlayers = 5;
        }
        for (int i = 0; i < startingPlayers; i++) {
            players.add(new Player("Player " + i));
        }
        players.add(new Player("FeuFeve").setHuman());
        return this;
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

    public List<String> getHumanPlayerPseudos() {
        List<String> humanPlayerPseudos = new ArrayList<>();
        for (Player player : players) {
            if (player.isHuman) {
                humanPlayerPseudos.add(player.name);
            }
        }
        return humanPlayerPseudos;
    }
}
