package game.main;

import com.google.gson.reflect.TypeToken;
import game.model.Building;
import game.model.Unit;
import utilities.FileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Config {

    public static Unit worker;
    public static List<Unit> units;
    public static List<Building> buildings;

    private static final String CONFIG_FOLDER_PATH = "config/";
    private static final String WORKER_FILEPATH = CONFIG_FOLDER_PATH + "worker.json";
    private static final String UNITS_FILEPATH = CONFIG_FOLDER_PATH + "units.json";
    private static final String BUILDINGS_FILEPATH = CONFIG_FOLDER_PATH + "buildings.json";

    public static void loadConfig() {
        worker = loadWorker();
        units = loadUnits();
        buildings = loadBuildings();
    }

    private static Unit loadWorker() {
        Type type = new TypeToken<Unit>(){}.getType();
        return (Unit) FileManager.loadFromJson(WORKER_FILEPATH, type);
    }

    @SuppressWarnings("unchecked")
    private static List<Unit> loadUnits() {
        Type type = new TypeToken<ArrayList<Unit>>(){}.getType();
        return (List<Unit>) FileManager.loadArrayFromJson(UNITS_FILEPATH, type);
    }

    @SuppressWarnings("unchecked")
    private static List<Building> loadBuildings() {
        Type type = new TypeToken<ArrayList<Building>>(){}.getType();
        return (List<Building>) FileManager.loadArrayFromJson(BUILDINGS_FILEPATH, type);
    }
}
