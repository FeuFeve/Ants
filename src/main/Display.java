package main;

import model.Player;
import model.Unit;
import wagu.Block;
import wagu.Board;
import wagu.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Display {

    public static void displayBaseUnitTable() {
        List<List<String>> unitsList = new ArrayList<>();
        for (Unit unit : Config.units) {
            unitsList.add(unit.getBaseStatsList());
        }
        displayUnitTable(unitsList);
    }

    public static void displayPlayerUnitTable(Player player) {
        List<List<String>> unitsList = new ArrayList<>();
        for (Unit unit : Config.units) {
            unitsList.add(unit.getBonusStatsList(player));
        }
        displayUnitTable(unitsList);
    }

    private static void displayUnitTable(List<List<String>> unitsList) {
        List<String> headersList = Arrays.asList("NAME", "HP", "ATTACK", "DEFENSE", "LAYING TIME", "FOOD COST");

        Board board = new Board(100);
        Table table = new Table(board, 100, headersList, unitsList);
        List<Integer> colWidthsListEdited = Arrays.asList(20, 8, 8, 8, 12, 10);
        table.setColWidthsList(colWidthsListEdited);
        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_MIDDLE_LEFT,
                Block.DATA_MIDDLE_RIGHT,
                Block.DATA_MIDDLE_RIGHT,
                Block.DATA_MIDDLE_RIGHT,
                Block.DATA_MIDDLE_RIGHT,
                Block.DATA_MIDDLE_RIGHT
        );
        table.setColAlignsList(colAlignList);
        Block tableBlock = table.tableToBlocks();
        board.setInitialBlock(tableBlock);
        board.build();
        String tableString = board.getPreview();
        System.out.println(tableString);
    }
}
