package controllers;

import game.main.GameManager;
import game.main.Launcher;
import game.main.SceneManager;
import game.model.World;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import game.main.ControllersManager;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuViewController implements Initializable {

    @FXML private ComboBox<String> worldsComboBox;
    @FXML private ComboBox<String> pseudosComboBox;

    private World currentMenuWorld;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("Initializing MainMenuViewController...");

        ControllersManager.mainMenuViewController = this;
        String[] savedWorlds = World.getSavedWorlds();
        worldsComboBox.setItems(FXCollections.observableArrayList(savedWorlds));

        System.out.println(" Done.");
    }

    public void refreshPseudosComboBox() {
        String worldName = worldsComboBox.getValue();
        currentMenuWorld = GameManager.loadWorldIfDifferent(worldName, currentMenuWorld);
        List<String> humanPlayerPseudos = currentMenuWorld.getHumanPlayerPseudos();
        pseudosComboBox.setItems(FXCollections.observableArrayList(humanPlayerPseudos));
    }

    public void login() {
        GameManager.world = currentMenuWorld;
        String worldName = currentMenuWorld.name;
        String pseudo = pseudosComboBox.getValue();
        GameManager.currentPlayer = currentMenuWorld.getPlayer(pseudo);
        System.out.println("Opening " + worldName + " as " + pseudo + ".");
        SceneManager.loadGameScene();
    }

    public void exit() {
        Launcher.exit();
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
