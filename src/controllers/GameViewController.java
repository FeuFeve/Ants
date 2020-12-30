package controllers;

import game.main.*;
import game.model.Unit;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;
import utilities.Date;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameViewController implements Initializable {

    @FXML MenuItem queenMenuItem;
    @FXML MenuItem armyMenuItem;

    @FXML MenuItem logoutMenuItem;
    @FXML MenuItem exitMenuItem;

    @FXML VBox unitsDetailsVBox;

    // QUEEN PAGE
    @FXML ScrollPane queenPage;
    @FXML VBox queenPageContent;
    List<Pair<HBox, UnitDetailsController>> unitHBoxList = new ArrayList<>();

    // ARMY PAGE
    @FXML ScrollPane armyPage;

    private static ScrollPane currentPage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("Initializing GameViewController...");

        ControllersManager.gameViewController = this;

//        HBox testHbox = new HBox(unitPlaceholder);

        System.out.println(" Done.");
    }

    private void initPagesWithConfig() {
        System.out.print("Initializing pages with the content of the config files...");
        // Initialize the pages with the content of the config files
        try {
            initQueenPage();
            // Other page inits...
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" Done.");
    }

    private void initQueenPage() throws IOException {
        queenPage.getContent().setOnScroll(scrollEvent -> Animator.slowScroll(queenPage, scrollEvent));

        FXMLLoader loader;
        for (Unit unit : Config.units) {
            loader = new FXMLLoader(getClass().getResource("../views/unit_details.fxml"));
            HBox unitHBox = loader.load();
            UnitDetailsController unitDetailsController = loader.getController();

            unitDetailsController.setUnit(unit);
            unitHBoxList.add(new Pair<>(unitHBox, unitDetailsController));

            unitsDetailsVBox.getChildren().add(unitHBox);
        }
    }

    public void load() {
        initPagesWithConfig();
        loadQueenPage();
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
