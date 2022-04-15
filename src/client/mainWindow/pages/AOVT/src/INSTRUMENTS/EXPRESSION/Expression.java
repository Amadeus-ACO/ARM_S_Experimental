package INSTRUMENTS.EXPRESSION;

import INSTRUMENTS.Instrument;
import INSTRUMENTS.Step;
import javafx.event.ActionEvent;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class Expression extends Instrument {

    private final MathField mathField;

    public MathField getMathField() {
        return mathField;
    }

    public Expression(int index, Step step) {
        super("Математическое выражение", index, step);
        mathField = new MathField();
        mathField.setEditMode(true);
        this.titledPane.setContent(mathField.getWebView());
        titledPane.setMinWidth(Region.USE_PREF_SIZE);
        titledPane.setMinHeight(Region.USE_PREF_SIZE);
    }

    @Override
    public String getInform() {
        return null;
    }

    @Override
    public void separate_digits(ActionEvent actionEvent) {

    }

    @Override
    public void checkValue() throws IllegalArgumentException {

    }
}
