package controllers;

import game.model.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UnitDetailsController {

    @FXML Label name;

    public Unit unit;


    public void setUnit(Unit unit) {
        this.unit = unit;
        name.setText(unit.name);
    }
}
