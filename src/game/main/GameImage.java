package game.main;

import game.model.Unit;
import javafx.scene.image.Image;

public class GameImage {

    private static final Image missing = new Image("file:src/images/missing.png");
    private static final String unitImagePath = "file:src/images/units/";
    private static final String imageExtension = ".png";

    public static Image getUnitImage(Unit unit) {
        Image image = new Image(unitImagePath + unit.name + imageExtension);
        if (image.isError())
            image = missing;
        return image;
    }
}
