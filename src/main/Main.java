package main;

import model.Army;
import model.Player;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        Player FeuFeve = new Player("FeuFeve");
        FeuFeve.debugModifyStats(0, 0, 0, 0);
        Army fArmy = new Army(FeuFeve);
        fArmy.add("Young dwarf", 186_000_000);
//        System.out.println(fArmy);

        Player OxyMore = new Player("OxyMore");
        Army oArmy = new Army(OxyMore);
        OxyMore.debugModifyStats(0, 0, 0, 0);
        oArmy.add("Young dwarf", 35_000_000);
//        System.out.println(oArmy);

        Army.attackIn("nest", fArmy, oArmy);

//        Display.displayBaseUnitTable();
//        Display.displayPlayerUnitTable(FeuFeve);
    }
}
