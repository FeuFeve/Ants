package controllers;

import game.main.Config;
import game.main.GameImage;
import game.main.GameManager;
import game.model.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import utilities.NumberFormatter;
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
        GameManager.currentPlayer.layingSpeedLevel = 110;
        GameManager.currentPlayer.recalculateLayingSpeedMultiplier();
        this.unit = null;

        unitImage.setImage(GameImage.getWorkerImage());

        hpImageView.setVisible(false);
        attackImageView.setVisible(false);
        defenseImageView.setVisible(false);
        hpLabel.setText("");
        attackLabel.setText("");
        defenseLabel.setText("");

        nameLabel.setText("Worker");

        double duration = Config.worker.layingTime * GameManager.currentPlayer.layingSpeedMultiplier;
        timeLabel.setText(StringFormatter.numberInSecToDuration(duration));
        foodCostLabel.setText(String.valueOf(Config.worker.foodCost));

        addQueenPageAmountObservable(amountTextField, Config.worker);
    }

    public void setUnit(Unit unit) {
        this.unit = unit;

        unitImage.setImage(GameImage.getUnitImage(unit));

        hpLabel.setText(String.valueOf(unit.hp));
        attackLabel.setText(String.valueOf(unit.attack));
        defenseLabel.setText(String.valueOf(unit.defense));
        
        nameLabel.setText(unit.name);

        double duration = unit.layingTime * GameManager.currentPlayer.layingSpeedMultiplier;
        timeLabel.setText(StringFormatter.numberInSecToDuration(duration));
        foodCostLabel.setText(String.valueOf(unit.foodCost));

        addQueenPageAmountObservable(amountTextField, unit);
    }

    private void addQueenPageAmountObservable(TextField textField, Unit unit) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            long amount = NumberFormatter.getQuantityInLong(newValue);
            if (amount == 0)
                amount = 1;

            double oneUnitDuration = unit.layingTime * GameManager.currentPlayer.layingSpeedMultiplier;
            timeLabel.setText(StringFormatter.numberInSecToDuration(oneUnitDuration * amount));
            foodCostLabel.setText(NumberFormatter.getQuantityInString(amount * unit.foodCost));
        });
    }
}
