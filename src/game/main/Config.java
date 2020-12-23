package game.main;

import com.google.gson.reflect.TypeToken;
import game.model.Building;
import game.model.Unit;
import utilities.FileManager;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    public static Unit worker;
    public static List<Unit> units;
    public static List<Building> buildings;

    private static final String CONFIG_FOLDER_PATH = "/config/";
    private static final String WORKER_FILE = "worker.json";
    private static final String UNITS_FILE = "units.json";
    private static final String BUILDINGS_FILE = "buildings.json";

    public static void loadConfigFor(File world) {
        System.out.print("Loading config files... ");

        String configFilePath = world.getPath() + CONFIG_FOLDER_PATH;
        worker = loadWorker(configFilePath);
        units = loadUnits(configFilePath);
        buildings = loadBuildings(configFilePath);

        System.out.println(" Done.");
    }

    private static Unit loadWorker(String configFilePath) {
        Type type = new TypeToken<Unit>(){}.getType();
        return (Unit) FileManager.loadFromJson(configFilePath + WORKER_FILE, type);
    }

    @SuppressWarnings("unchecked")
    private static List<Unit> loadUnits(String configFilePath) {
        Type type = new TypeToken<ArrayList<Unit>>(){}.getType();
        return (List<Unit>) FileManager.loadArrayFromJson(configFilePath + UNITS_FILE, type);
    }

    @SuppressWarnings("unchecked")
    private static List<Building> loadBuildings(String configFilePath) {
        Type type = new TypeToken<ArrayList<Building>>(){}.getType();
        return (List<Building>) FileManager.loadArrayFromJson(configFilePath + BUILDINGS_FILE, type);
    }

    public static boolean hasValidConfig(File world) {
        File configFile = new File(world.getPath() + CONFIG_FOLDER_PATH);
        List<String> content;
        try {
            content = Arrays.asList(configFile.list());
        } catch (NullPointerException e) {
            return false;
        }
        return (content.contains(WORKER_FILE)
                && content.contains(UNITS_FILE)
                && content.contains(BUILDINGS_FILE));
    }
}
