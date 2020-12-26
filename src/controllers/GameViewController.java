package controllers;

import game.main.GameManager;
import game.main.Launcher;
import game.main.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import game.main.ControllersManager;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.Date;

import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    @FXML MenuItem queenMenuItem;
    @FXML MenuItem armyMenuItem;

    @FXML MenuItem logoutMenuItem;
    @FXML MenuItem exitMenuItem;

    @FXML ScrollPane queenPage;
    @FXML ScrollPane armyPage;

    @FXML VBox queenPageContent;
    @FXML HBox unitPlaceholder;

    private static ScrollPane currentPage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("Initializing GameViewController...");

        ControllersManager.gameViewController = this;

//        HBox testHbox = new HBox(unitPlaceholder);

        System.out.println(" Done.");
    }

    public void loadQueenPage() {
        loadScrollPage(queenPage, queenPageContent);
    }

    public void loadArmyPage() {
        loadPage(armyPage);
    }

    private void loadPage(ScrollPane page) {
        if (currentPage == null) {
            currentPage = page;
            currentPage.setDisable(false);
            currentPage.setVisible(true);
        }
        else if (currentPage != page) {
            currentPage.setVisible(false);
            currentPage.setDisable(true);
            page.setDisable(false);
            page.setVisible(true);
            currentPage = page;
        }
    }

    private void loadScrollPage(ScrollPane page, VBox content) {
        loadPage(page);
        content.setPrefWidth(page.getWidth() - 15);
    }

    public void logout() {
        GameManager.saveAndCloseWorld();
        SceneManager.loadMainMenuScene();
    }

    public void exit() {
        GameManager.saveAndCloseWorld();
        Launcher.exit();
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
