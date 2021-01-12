package controllers;

import game.main.Config;
import game.main.GameImage;
import game.main.GameManager;
import game.model.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import utilities.StringFormatter;

public class UnitDetailsController {

    @FXML ImageView unitImage;

    @FXML ImageView hpImageView;
    @FXML ImageView attackImageView;
    @FXML ImageView defenseImageView;
    @FXML Label hpLabel;
    @FXML Label attackLabel;
    @FXML Label defenseLabel;

    @FXML Label nameLabel;
    @FXML Label descriptionLabel;
    @FXML Label requiredLabel;

    @FXML TextField amountTextField;
    @FXML Label timeLabel;
    @FXML Label foodCostLabel;

    public Unit unit;


    public void setIsWorker() {
        this.unit = null;

        unitImage.setImage(GameImage.getWorkerImage());

        hpImageView.setVisible(false);
        attackImageView.setVisible(false);
        defenseImageView.setVisible(false);
        hpLabel.setText("");
        attackLabel.setText("");
        defenseLabel.setText("");

        nameLabel.setText("Worker");

        double time = Config.worker.layingTime * GameManager.currentPlayer.layingSpeedMultiplier;
        timeLabel.setText(StringFormatter.smallNumber(time, 2) + "s");
        foodCostLabel.setText(String.valueOf(Config.worker.foodCost));
    }

    public void setUnit(Unit unit) {
        this.unit = unit;

        unitImage.setImage(GameImage.getUnitImage(unit));

        hpLabel.setText(String.valueOf(unit.hp));
        attackLabel.setText(String.valueOf(unit.attack));
        defenseLabel.setText(String.valueOf(unit.defense));
        
        nameLabel.setText(unit.name);

        double time = unit.layingTime * GameManager.currentPlayer.layingSpeedMultiplier;
        timeLabel.setText(StringFormatter.smallNumber(time, 2) + "s");
        foodCostLabel.setText(String.valueOf(unit.foodCost));
    }
}
