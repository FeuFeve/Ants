package controllers;

import javafx.fxml.Initializable;
import game.main.ControllersManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuViewController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing MainMenuViewController...");
        ControllersManager.mainMenuViewController = this;
        System.out.println(" Done.");
    }

//    public void newGame() {
//        DataManager.createNewGame();
//        SceneManager.loadGameScene();
//        Platform.runLater(() -> ControllersManager.gameViewController.updateView(DataManager.currentGame));
//    }

//    public void loadGame() throws IOException {
//        if (DataManager.loadGame() == 1) {
//            SceneManager.loadGameScene();
//            Platform.runLater(() -> ControllersManager.gameViewController.updateView(DataManager.currentGame));
//        }
//    }
}