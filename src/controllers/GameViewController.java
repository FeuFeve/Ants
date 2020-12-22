package controllers;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import game.main.ControllersManager;
import utilities.Date;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing GameViewController...");
        ControllersManager.gameViewController = this;
        System.out.println(" Done.");
    }

    public void updateView() {
        Platform.runLater(() -> {
            String time = Date.getRealTimeMs();
            System.out.println("(" + Date.getRealDateAndTime() + ") Updating game view.");
//            currentSystemTime.setText("Current system time: " + time);
//            currentUpdateIndex.setText("Current update index: " + game.totalTicks);
        });
    }

//    public void save() throws IOException {
//        if (GameManager.isRunning) {
//            GameManager.stop();
//            DataManager.saveGame();
//            GameManager.start();
//        }
//        else {
//            DataManager.saveGame();
//        }
//    }

//    public void backToMainMenu() {
//        DataManager.currentGame = null;
//        GameManager.stop();
//        SceneManager.loadMainMenuScene();
//    }
}
