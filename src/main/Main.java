package main;

import model.Army;
import model.Player;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        Player FeuFeve = new Player("FeuFeve");
        FeuFeve.debugModifyStats(23, 21, 999, 777, 13);
        Army fArmy = new Army(FeuFeve);
        fArmy.add("Young dwarf", 1_000_000_000);
        fArmy.add("Dwarf", 60_000_000);
        fArmy.add("Young soldier", 500_000_000);
        fArmy.add("Soldier", 100_000_000);
        fArmy.add("Doorkeeper", 10_000_000);
        fArmy.add("Top soldier", 100_000_000);
        fArmy.add("Tank", 300_000_000);
        fArmy.add("Top tank", 350_000_000);
        fArmy.add("Killer", 25_000_000);
        fArmy.add("Top killer", 50_000_000);
//        System.out.println(fArmy);

        Player OxyMore = new Player("OxyMore");
        Army oArmy = new Army(OxyMore);
        OxyMore.debugModifyStats(19, 24, 666, 27, 123);
        /*
        800 000 000 Jeunes Soldates Naines,
        400 000 000 Soldates Naines
        30 000 000 Naines d’Elites
        20 000 000 Concierges
        15 000 000 Concierges d’élites
        100 000 000 Artilleuses
        150 000 000 Artilleuses d’élites
        50 000 000 Tanks d’élites
        125 000 000 Tueuses d’élites
        */
        oArmy.add("Young dwarf", 800_000_000);
        oArmy.add("Dwarf", 400_000_000);
        oArmy.add("Top dwarf", 30_000_000);
        oArmy.add("Doorkeeper", 20_000_000);
        oArmy.add("Top doorkeeper", 15_000_000);
        oArmy.add("Fire ant", 100_000_000);
        oArmy.add("Top fire ant", 150_000_000);
        oArmy.add("Top tank", 50_000_000);
        oArmy.add("Top killer", 125_000_000);
//        System.out.println(oArmy);

        Army.attackIn("nest", fArmy, oArmy);

//        Display.displayBaseUnitTable();
//        Display.displayPlayerUnitTable(FeuFeve);
    }
}
