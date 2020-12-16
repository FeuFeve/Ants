package main;

import javafx.util.Pair;
import model.Army;
import model.Player;
import model.Unit;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        Player FeuFeve = new Player("FeuFeve");
        Army fArmy = new Army();
        fArmy.player = FeuFeve;
        for (Unit unit : Config.units) {
            if (unit.name.equals("Young soldier")) {
                fArmy.add(new Pair<>(unit, 100));
            }
        }
        System.out.println(fArmy);

        Display.displayBaseUnitTable();
        Display.displayPlayerUnitTable(FeuFeve);
    }
}
