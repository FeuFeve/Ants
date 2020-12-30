package game.main;

import controllers.GameViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage window;
    private static Scene mainMenuScene, gameScene;
    private static GameViewController gameViewController;


    public static void init(Stage window) throws IOException {
        SceneManager.window = window;

        mainMenuScene = new Scene(FXMLLoader.load(SceneManager.class.getResource("../../views/main_menu_view.fxml")));

        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("../../views/game_view.fxml"));
        gameScene = new Scene(loader.load());
        gameViewController = loader.getController();
    }

    public static void loadMainMenuScene() {
        window.setScene(mainMenuScene);
        window.setFullScreen(true);
    }

    public static void loadGameScene() {
        window.setScene(gameScene);
        window.setFullScreen(true);
        gameViewController.load();
    }
}
