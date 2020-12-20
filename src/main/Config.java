package main;

import com.google.gson.reflect.TypeToken;
import model.Resource;
import model.Unit;
import utilities.FileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Config {

    public static Unit worker;
    public static List<Unit> units;
    public static List<Resource> resources;

    private static final String CONFIG_FOLDER_PATH = "config/";
    private static final String WORKER_FILEPATH = CONFIG_FOLDER_PATH + "worker.json";
    private static final String UNITS_FILEPATH = CONFIG_FOLDER_PATH + "units.json";
    private static final String RESOURCES_FILEPATH = CONFIG_FOLDER_PATH + "resources.json";

    public static void loadConfig() {
        worker = loadWorker();
        units = loadUnits();
        resources = loadResources();
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
    private static List<Resource> loadResources() {
        Type type = new TypeToken<ArrayList<Resource>>(){}.getType();
        return (List<Resource>) FileManager.loadArrayFromJson(RESOURCES_FILEPATH, type);
    }
}
