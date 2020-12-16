package main;

import javafx.util.Pair;
import model.Army;
import model.Player;
import model.Unit;
import utilities.StringFormatter;

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

        Player OxyMore = new Player("OxyMore");
        Army oArmy = new Army();
        oArmy.player = OxyMore;
        for (Unit unit : Config.units) {
            if (unit.name.equals("Tank")) {
                oArmy.add(new Pair<>(unit, 300));
            }
        }
        System.out.println(oArmy);

        fArmy.attackInHf(oArmy);
        fArmy.attackInDome(oArmy);
        fArmy.attackInNest(oArmy);

        String formattedResult = StringFormatter.firstLeftSecondRightAlign(24, "HP bonus (HF):", "100%", true);
        System.out.println("|" + formattedResult + "|");

//        Display.displayBaseUnitTable();
//        Display.displayPlayerUnitTable(FeuFeve);
    }
}
