package controllers;

import game.main.GameImage;
import game.model.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class UnitDetailsController {

    @FXML ImageView unitImage;
    @FXML Label name;

    public Unit unit;


    public void setIsWorker() {
        this.unit = null;
        name.setText("Worker");
        unitImage.setImage(GameImage.getWorkerImage());
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
        name.setText(unit.name);
        unitImage.setImage(GameImage.getUnitImage(unit));
    }
}
