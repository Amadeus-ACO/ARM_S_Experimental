package INSTRUMENTS.TABLE;

import javafx.scene.paint.Color;

public class TruthTableRow {

    public Integer result;

    public Integer[] args;

    public Color color;

    public TruthTableRow(Integer val, Color color) {
        this.color = color;
        args = new Integer[val];
        for (int i = 0; i < val; i++) {
            args[i] = null;
        }
    }
}
