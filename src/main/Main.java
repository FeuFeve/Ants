package main;

import model.Army;
import model.Player;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        Player FeuFeve = new Player("FeuFeve");
        FeuFeve.debugModifyStats(26, 25, 31, 29);
        Army fArmy = new Army(FeuFeve);
        fArmy.add("Young dwarf", 1_500_000_000);
        fArmy.add("Young soldier", 500_000_000);
        fArmy.add("Tank", 125_000_000);
        fArmy.add("Killer", 66_666_666);
//        System.out.println(fArmy);

        Player OxyMore = new Player("OxyMore");
        Army oArmy = new Army(OxyMore);
        OxyMore.debugModifyStats(24, 27, 28, 32);
        oArmy.add("Young dwarf", 2_500_000_000L);
        oArmy.add("Young soldier", 123_456_789);
        oArmy.add("Tank", 600_000_000);
        oArmy.add("Killer", 100_000_000);
//        System.out.println(oArmy);

        Army.attackIn("nest", fArmy, oArmy);

//        Display.displayBaseUnitTable();
//        Display.displayPlayerUnitTable(FeuFeve);
    }
}
