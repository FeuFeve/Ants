package game.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import utilities.Date;

import java.time.LocalDateTime;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Welcome back!");

        SceneManager.init(primaryStage);
        Config.loadConfig();

        primaryStage.setOnCloseRequest(t -> exit());
        primaryStage.setTitle("Ants");
//        primaryStage.setResizable(false);

        SceneManager.loadMainMenuScene();
        primaryStage.show();
    }

    public void exit() {
        System.out.println("Exiting...");
        // TODO: save the game
        Platform.exit();
        System.exit(0);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void main2(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        LocalDateTime ldtNow = Date.getCurrentDatePlus(0);
        System.out.println("ldtNow = " + ldtNow);
        LocalDateTime ldt2 = Date.getCurrentDatePlus(8640000);
        System.out.println("ldt2 = " + ldt2);
        LocalDateTime ldt2_10 = Date.getDateWithMultiplier(ldtNow, ldt2, 0.9);
        System.out.println("ldt2_10 = " + ldt2_10);

//        Player FeuFeve = new Player("FeuFeve");
//        FeuFeve.debugModifyStats(23, 24, 27, 28, 13);
//        Army fArmy = new Army(FeuFeve, "nest");
//        fArmy.add("Young dwarf", 10_000_000_000L);
//        fArmy.add("Dwarf", 60_000_000);
//        fArmy.add("Top dwarf", 123_456_789);
//        fArmy.add("Young soldier", 1_500_000_000);
//        fArmy.add("Soldier", 800_000_000);
//        fArmy.add("Doorkeeper", 10_000_000);
//        fArmy.add("Top doorkeeper", 5_000_000);
//        fArmy.add("Fire ant", 120_000);
//        fArmy.add("Top fire ant", 880_000);
//        fArmy.add("Top soldier", 100_000_000);
//        fArmy.add("Tank", 300_000_000);
//        fArmy.add("Top tank", 350_000_000);
//        fArmy.add("Killer", 25_000_000);
//        fArmy.add("Top killer", 50_000_000);
//
//        Player OxyMore = new Player("OxyMore");
//        OxyMore.debugModifyStats(25, 26, 29, 27, 9);
//        Army oArmy = new Army(OxyMore, "dome");
//        oArmy.add("Young dwarf", 1_800_000_000);
//        oArmy.add("Dwarf", 700_000_000);
//        oArmy.add("Top dwarf", 450_000_000);
//        oArmy.add("Young soldier", 123_123_123);
//        oArmy.add("Soldier", 888_888_888);
//        oArmy.add("Doorkeeper", 20_000_000);
//        oArmy.add("Top doorkeeper", 105_000_000);
//        oArmy.add("Fire ant", 100_000_000);
//        oArmy.add("Top fire ant", 150_000_000);
//        oArmy.add("Top soldier", 12_600_000);
//        oArmy.add("Tank", 7_777_777);
//        oArmy.add("Top tank", 50_000_000);
//        oArmy.add("Killer", 155_625_375);
//        oArmy.add("Top killer", 333_000_000);
//
//        Army oArmy2 = new Army(OxyMore, "nest");
//        oArmy2.add("Young dwarf", 3_500_000_000L);
//        oArmy2.add("Dwarf", 50_000);
//        oArmy2.add("Soldier", 1_000_000_000);
//        oArmy2.add("Top doorkeeper", 50_000_000);
//        oArmy2.add("Top fire ant", 200_000_000);
//        oArmy2.add("Tank", 125_000_000);
//        oArmy2.add("Top tank", 500_000_000);
//        oArmy2.add("Killer", 100_000_000);
//
//        FeuFeve.displayArmies();
//        OxyMore.displayArmies();
//
//        new Attack(fArmy, OxyMore, "nest").fight();
//
//        FeuFeve.displayArmies();
//        OxyMore.displayArmies();

//        Display.displayBaseUnitTable();
//        Display.displayPlayerUnitTable(FeuFeve);
    }
}
