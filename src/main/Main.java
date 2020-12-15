package main;

import model.Unit;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome back!");

        Config.loadConfig();

        System.out.println(Config.worker);
        for (Unit unit : Config.units) {
            System.out.println(unit);
        }
    }
}
