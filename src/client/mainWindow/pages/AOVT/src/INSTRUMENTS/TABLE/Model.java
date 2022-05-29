package INSTRUMENTS.TABLE;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Model {

    @Setter
    @Getter
    private Integer countParams;
    @Setter
    @Getter
    ArrayList<TruthTableRow> rows = new ArrayList<>();

    public Model(int i, ArrayList<Color> list) {

        countParams = i;
        for (int j = 0; j < Math.pow(2,i); j++) {
            rows.add(new TruthTableRow(i,list.get(j)));
        }
    }


}
