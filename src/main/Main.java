package main;

import model.Player;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        Display.displayBaseUnitTable();
        Display.displayPlayerUnitTable(new Player("FeuFeve"));
    }
}
