package main;

import event.Attack;
import model.Army;
import model.Player;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        Player FeuFeve = new Player("FeuFeve");
        FeuFeve.debugModifyStats(23, 24, 27, 28, 13);
        Army fArmy = new Army(FeuFeve, "nest");
        fArmy.add("Young dwarf", 10_000_000_000L);
        fArmy.add("Dwarf", 60_000_000);
        fArmy.add("Top dwarf", 123_456_789);
        fArmy.add("Young soldier", 1_500_000_000);
        fArmy.add("Soldier", 800_000_000);
        fArmy.add("Doorkeeper", 10_000_000);
        fArmy.add("Top doorkeeper", 5_000_000);
        fArmy.add("Fire ant", 120_000);
        fArmy.add("Top fire ant", 880_000);
        fArmy.add("Top soldier", 100_000_000);
        fArmy.add("Tank", 300_000_000);
        fArmy.add("Top tank", 350_000_000);
        fArmy.add("Killer", 25_000_000);
        fArmy.add("Top killer", 50_000_000);

        Player OxyMore = new Player("OxyMore");
        OxyMore.debugModifyStats(25, 26, 29, 27, 9);
        Army oArmy = new Army(OxyMore, "dome");
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

        FeuFeve.displayArmies();
        OxyMore.displayArmies();

        new Attack(fArmy, OxyMore, "nest").fight();

        FeuFeve.displayArmies();
        OxyMore.displayArmies();

//        Display.displayBaseUnitTable();
//        Display.displayPlayerUnitTable(FeuFeve);
    }
}
