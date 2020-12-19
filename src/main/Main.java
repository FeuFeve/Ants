package main;

import model.Army;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        double number = 2.6705882366474454281495744954976; // JS --> JSN
//        double number = 2.8281938459112867071133022141185; // TK --> JS
//        double number = 0.35358255453596412656400032412321; // JS --> Tk
//        double number = 7.5529414113227742329879602799296; // Tk --> JSN
//        double number = 0.70627062858372669676889863198172; // Tk --> Tu
//        double number = 1.4158878510855441065159365739173; // Tu --> Tk
        List<Integer> listOfI = new ArrayList<>();
        for (int i = 1; i <= 100_000; i++) {
            double multiple = number * i;
            double precision = 0.01;
            double modulus = multiple % 1;
            if (modulus < (precision / 10) || modulus > (1 - precision / 10)) {
                boolean multipleAlreadyInList = false;
                for (int j : listOfI) {
                    if (i % j == 0) {
                        multipleAlreadyInList = true;
                        break;
                    }
                }
                if (!multipleAlreadyInList) {
                    listOfI.add(i);
                    System.out.println("i=" + i + ", " + multiple);
                }
            }
        }

//        Player FeuFeve = new Player("FeuFeve");
//        FeuFeve.debugModifyStats(0, 0, 0, 0);
//        Army fArmy = new Army(FeuFeve);
//        fArmy.add("Young dwarf", 186_000_000);
////        System.out.println(fArmy);
//
//        Player OxyMore = new Player("OxyMore");
//        Army oArmy = new Army(OxyMore);
//        OxyMore.debugModifyStats(0, 0, 0, 0);
//        oArmy.add("Young dwarf", 35_000_000);
////        System.out.println(oArmy);
//
//        Army.attackIn("nest", fArmy, oArmy);

//        Display.displayBaseUnitTable();
//        Display.displayPlayerUnitTable(FeuFeve);
    }
}
