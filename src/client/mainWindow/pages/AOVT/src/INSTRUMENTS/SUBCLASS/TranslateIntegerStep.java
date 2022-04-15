package INSTRUMENTS.SUBCLASS;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


public class TranslateIntegerStep {
    /*
    * Шапка, инструмент деления.
    *
    * Шапка: Номер шага, результат FLOWPANE: LABEL, ( label + textfield)
    *
    * Всё вместе находится в VBox
    * */

    VBox translateIntegerStepVBox = new VBox();
    FlowPane Header = new FlowPane();
    Label stepLabel;
    TextField stepResult = new TextField();
    TitledPane instrumentPane = new TitledPane();
    int stepNumber;

    public TranslateIntegerStep(int stepNumber) {
        this.stepLabel = new Label("Шаг " + stepNumber + ".");
        this.stepNumber = stepNumber;
        Label label = new Label(" | Результат шага перевода: ");
        this.Header.getChildren().addAll(this.stepLabel, label, this.stepResult);

        this.translateIntegerStepVBox.getChildren().addAll(this.Header, this.instrumentPane);

    }
}
