package game.main;

import com.google.gson.reflect.TypeToken;
import game.model.World;
import utilities.FileManager;

import java.lang.reflect.Type;

public class GameManager {

    public static World world;

    static Thread gameThread;


    public static void saveWorld() {
        if (world != null) {
            System.out.print("Saving " + world.name + "...");
            FileManager.saveToJson(world, World.WORLDS_FOLDER_PATH + world.name + "/save.json");
            System.out.println(" Done.");
        }
    }

    public static void loadWorld(String worldName) {
        if (world == null || !worldName.equals(world.name)) {
            System.out.println("Loading " + worldName + "...");
            Config.loadConfigFor(worldName);
            Type type = new TypeToken<World>() {
            }.getType();
            world = (World) FileManager.loadFromJson(World.WORLDS_FOLDER_PATH + worldName + "/save.json", type);
            System.out.println("# Done.");
        }
    }
}
