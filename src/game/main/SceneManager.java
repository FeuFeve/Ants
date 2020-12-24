package game.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage window;
    private static Scene mainMenuScene, gameScene;


    public static void init(Stage window) throws IOException {
        SceneManager.window = window;

        mainMenuScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../../views/main_menu_view.fxml")));
        gameScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../../views/game_view.fxml")));
    }

    public static void loadMainMenuScene() {
        window.setScene(mainMenuScene);
        window.setFullScreen(true);
    }

    public static void loadGameScene() {
        window.setScene(gameScene);
        window.setFullScreen(true);
    }
}
