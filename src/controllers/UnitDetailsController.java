package controllers;

import game.main.Config;
import game.main.GameImage;
import game.main.GameManager;
import game.model.Unit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML ImageView amountImageView;
    @FXML TextField amountTextField;
    @FXML Label timeLabel;
    @FXML Label foodCostLabel;

    @FXML Button layDownButton;

    public Unit unit;


    public void setIsWorker() {
        setUnit(Config.worker);
    }

    public void setUnit(Unit unit) {
        this.unit = unit;

        Platform.runLater(() -> {
            unitImage.setImage(GameImage.getUnitImage(unit));

            if (unit == Config.worker) {
                hpImageView.setVisible(false);
                attackImageView.setVisible(false);
                defenseImageView.setVisible(false);
                hpLabel.setText("");
                attackLabel.setText("");
                defenseLabel.setText("");
            }
            else {
                hpLabel.setText(String.valueOf(unit.hp));
                attackLabel.setText(String.valueOf(unit.attack));
                defenseLabel.setText(String.valueOf(unit.defense));
            }

            nameLabel.setText(unit.name);
            descriptionLabel.setText(unit.description);

            if (unit.isLayable) {
                double duration = unit.layingTime * GameManager.currentPlayer.layingSpeedMultiplier;
                timeLabel.setText(StringFormatter.numberInSecToDuration(duration));

                addQueenPageAmountObservable(amountTextField, unit);
            }
            else {
                amountImageView.setVisible(false);
                amountTextField.setVisible(false);
                timeLabel.setText("Not layable");
                layDownButton.setVisible(false);
            }
            foodCostLabel.setText(String.valueOf(unit.foodCost));
        });
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
