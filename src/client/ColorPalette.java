package client;

import entity.work.GivenTask;
import javafx.scene.paint.Color;

public class ColorPalette {

    public static Color getCompleteStatusColor(GivenTask.COMPLETE_STATUS completeStatus) {
        return switch (completeStatus) {
            case GIVEN -> Color.web("#f4f4f4");
            case IN_WORK -> Color.web("#A3F8F8");
            case COMPLETED -> Color.web("#AEFFB0");
            case ON_FIX -> Color.LIGHTPINK;
            case WAITING_FOR_CHECK, WAITING_FOR_FINAL_TEST_CHECK -> Color.web("0xFFFB88");
        };
    }
}
