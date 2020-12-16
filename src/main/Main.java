package main;

import javafx.util.Pair;
import model.Army;
import model.Player;
import model.Unit;

import javax.swing.*;

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

        Display.displayBaseUnitTable();
        Display.displayPlayerUnitTable(FeuFeve);

        final String password, message = "Enter password";
        if( System.console() == null ) {
            final JPasswordField pf = new JPasswordField();
            password = JOptionPane.showConfirmDialog( null, pf, message, JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION ? new String( pf.getPassword() ) : "";
        }
        else
            password = new String( System.console().readPassword( "%s> ", message ) );

        System.out.println("THE PASSWORD WAS: " + password);
    }
}
