package client.mainWindow.pages.labPages.base.formulationTask;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

public class FormulationTaskView {

    private final FormulationTaskController formulationTaskController;
    private GridPane root;
    private WebView webView;

    public FormulationTaskView(FormulationTaskController formulationTaskController) {
        this.formulationTaskController = formulationTaskController;
    }

    public void init(GridPane root, Button readHelpButton, WebView webView) {
        this.root = root;
        VBox.setVgrow(root, Priority.ALWAYS);
        VBox.setMargin(root, new Insets(5,5,5,5));

        this.webView = webView;
        GridPane.setColumnSpan(webView, 2);

        Label formulationTaskLabel = new Label("Постановка задачи");
        formulationTaskLabel.setFont(Font.font("Lucida Sans", 25));

        // Настройка столбцов
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setHalignment(HPos.LEFT);
        root.getColumnConstraints().add(column0);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        root.getColumnConstraints().add(column1);
        for (ColumnConstraints column : root.getColumnConstraints())
            column.setHgrow(Priority.ALWAYS);

        // Настройка строк
        root.getRowConstraints().addAll(new RowConstraints(), new RowConstraints());
        root.getRowConstraints().get(1).setVgrow(Priority.ALWAYS);

        root.add(formulationTaskLabel, 0, 0);
        root.add(readHelpButton, 1, 0);
        root.add(webView, 0, 1);
    }
}
