package controllers;

import game.main.GameManager;
import game.main.Launcher;
import game.model.World;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import game.main.ControllersManager;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuViewController implements Initializable {

    @FXML private ComboBox<String> worldsComboBox;
    @FXML private TextField pseudoTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("Initializing MainMenuViewController...");

        ControllersManager.mainMenuViewController = this;
        String[] savedWorlds = World.getSavedWorlds();
        worldsComboBox.setItems(FXCollections.observableArrayList(savedWorlds));

        System.out.println(" Done.");
    }

    public void login() {
        String worldName = worldsComboBox.getValue();
        String pseudo = pseudoTextField.getText();
        GameManager.loadWorld(worldName);
        if (GameManager.world.hasHumanPlayer(pseudo))
            System.out.println(pseudo + " is in " + worldName); // TODO: Go the game view
        else
            System.out.println(pseudo + " is NOT in " + worldName); // TODO: Display an error message to the player
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
