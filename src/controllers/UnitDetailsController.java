package controllers;

import game.main.GameImage;
import game.model.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
    }

    public void setUnit(Unit unit) {
        this.unit = unit;

        unitImage.setImage(GameImage.getUnitImage(unit));

        hpLabel.setText(String.valueOf(unit.hp));
        attackLabel.setText(String.valueOf(unit.attack));
        defenseLabel.setText(String.valueOf(unit.defense));
        
        nameLabel.setText(unit.name);
    }
}
