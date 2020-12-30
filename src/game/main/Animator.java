package game.main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;

public class Animator {

    public static void slowScroll(ScrollPane scrollPane, ScrollEvent scrollEvent) {
        double to = scrollPane.getVvalue();
        if (scrollEvent.getDeltaY() < 0)
            to += 0.2;
        else
            to -= 0.2;

        if (to == scrollPane.getVvalue())
            return;
        else if (to > 1)
            to = 1;
        else if (to < 0)
            to = 0;

        Animation animation = new Timeline(
                new KeyFrame(Duration.seconds(0.1),
                        new KeyValue(scrollPane.vvalueProperty(), to)));
        animation.play();
    }
}
