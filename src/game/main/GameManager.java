package game.main;

import com.google.gson.reflect.TypeToken;
import game.model.Player;
import game.model.World;
import org.jetbrains.annotations.Nullable;
import utilities.FileManager;

import java.lang.reflect.Type;

public class GameManager {

    public static World world;
    public static Player currentPlayer;

    static Thread gameThread;


    public static void saveWorld() {
        if (world != null) {
            System.out.print("Saving " + world.name + "...");
            FileManager.saveToJson(world, World.WORLDS_FOLDER_PATH + world.name + "/save.json");
            System.out.println(" Done.");
        }
    }

    public static void saveAndCloseWorld() {
        saveWorld();
        System.out.println("Closing " + world.name + ".");
        world = null;
        currentPlayer = null;
    }

    public static World loadWorldIfDifferent(String worldName, @Nullable World previousWorld) {
        if (previousWorld == null || !worldName.equals(previousWorld.name)) {
            System.out.println("Loading " + worldName + "...");
            Config.loadConfigFor(worldName);
            Type type = new TypeToken<World>() {
            }.getType();
            World world = (World) FileManager.loadFromJson(World.WORLDS_FOLDER_PATH + worldName + "/save.json", type);
            System.out.println("# Done.");
            return world;
        }
        return previousWorld;
    }
}
