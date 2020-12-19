package main;

import model.Army;
import model.Player;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        Player FeuFeve = new Player("FeuFeve");
        FeuFeve.debugModifyStats(3, 27, 999, 777, 0);
        Army fArmy = new Army(FeuFeve);
        fArmy.add("Young dwarf", 5_000_000_000L);
        fArmy.add("Dwarf", 60_000_000);
        fArmy.add("Top dwarf", 123_456_789);
        fArmy.add("Young soldier", 500_000_000);
        fArmy.add("Soldier", 100_000_000);
        fArmy.add("Doorkeeper", 10_000_000);
        fArmy.add("Top doorkeeper", 5_000_000);
        fArmy.add("Fire ant", 120_000);
        fArmy.add("Top fire ant", 880_000);
        fArmy.add("Top soldier", 100_000_000);
        fArmy.add("Tank", 300_000_000);
        fArmy.add("Top tank", 350_000_000);
        fArmy.add("Killer", 25_000_000);
        fArmy.add("Top killer", 50_000_000);
//        System.out.println(fArmy);

        Player OxyMore = new Player("OxyMore");
        Army oArmy = new Army(OxyMore);
        OxyMore.debugModifyStats(31, 29, 31, 37, 9);
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
        oArmy.add("Young dwarf", 1_800_000_000);
        oArmy.add("Dwarf", 700_000_000);
        oArmy.add("Top dwarf", 450_000_000);
        oArmy.add("Young soldier", 123_123_123);
        oArmy.add("Soldier", 888_888_888);
        oArmy.add("Doorkeeper", 20_000_000);
        oArmy.add("Top doorkeeper", 105_000_000);
        oArmy.add("Fire ant", 100_000_000);
        oArmy.add("Top fire ant", 150_000_000);
        oArmy.add("Top soldier", 12_600_000);
        oArmy.add("Tank", 7_777_777);
        oArmy.add("Top tank", 50_000_000);
        oArmy.add("Killer", 155_625_375);
        oArmy.add("Top killer", 333_000_000);
//        System.out.println(oArmy);

        Army.attackIn("nest", fArmy, oArmy);

//        Display.displayBaseUnitTable();
//        Display.displayPlayerUnitTable(FeuFeve);
    }
}
