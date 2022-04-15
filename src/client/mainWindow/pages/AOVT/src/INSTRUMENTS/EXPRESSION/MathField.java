package INSTRUMENTS.EXPRESSION;

import INSTRUMENTS.Instrument;
import INSTRUMENTS.Step;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MathField {

    private final WebView webView = new WebView();
    private final Button editButton = new Button();
    private final Button saveButton = new Button();

    public WebView getWebView() {
        return webView;
    }
    public Button getEditButton() {
        return editButton;
    }
    public Button getSaveButton() {
        return saveButton;
    }

    private int fieldRestriction = 15; // Максимальное количество символов
    private float webViewHeight = 50; // Высота области

    private float webViewEditWidth = fieldRestriction * 17; // Ширина области при редактировании
    private float webViewSaveWidth = webViewEditWidth - 105; // Ширина области при сохранении
    private float fieldWidth = webViewEditWidth - 110; // Ширина поля

    public void setWebViewEditWidth(float webViewEditWidth) {
        this.webViewEditWidth = webViewEditWidth;
        webView.setPrefWidth(webViewEditWidth);
    }

    public void setWebViewSaveWidth(float webViewSaveWidth) {
        this.webViewSaveWidth = webViewSaveWidth;
        webView.setPrefWidth(webViewSaveWidth);
    }

    public void setWebViewHeight(float webViewHeight) {
        this.webViewHeight = webViewHeight;
        webView.setPrefHeight(webViewHeight);
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
        webView.getEngine().executeScript("configField(" + fieldRestriction + "," + fieldWidth + ")");
    }

    public void setFieldRestriction(int fieldRestriction) {
        this.fieldRestriction = fieldRestriction;
        webView.getEngine().executeScript("configField(" + fieldRestriction + "," + fieldWidth + ")");
    }

    private boolean editMode = true;
    private boolean loaded = false;

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        if (loaded)
            changeMode();
    }

    private void changeMode() {
        if (editMode)
            onEdit();
        else
            onSave();
    }

    private void onEdit() {
        webView.setPrefWidth(webViewEditWidth);
        webView.setDisable(false);
        webView.getEngine().executeScript("editAction()");

        if (editButton.getParent() != null) {
            Pane pane = (Pane) editButton.getParent();
            pane.getChildren().remove(editButton);
            pane.getChildren().add(saveButton);
        }
    }

    private void onSave() {
        webView.setPrefWidth(webViewSaveWidth);
        webView.setDisable(true);
        System.out.println(webView.getEngine());
        webView.getEngine().executeScript("saveAction()");

        if (saveButton.getParent() != null) {
            Pane pane = (Pane) saveButton.getParent();
            pane.getChildren().remove(saveButton);
            pane.getChildren().add(editButton);
        }
    }

    public static final String htmlLocation = "mathQuill/studentAnswerField.html";
    private static final String cssLocation = "mathQuill/mathquill.css";

    public MathField() {
        webView.setPrefSize(webViewSaveWidth, webViewHeight);

        String htmlPath = this.getClass().getClassLoader().getResource(htmlLocation).toString();
        String cssPath = this.getClass().getClassLoader().getResource(cssLocation).toString();
        webView.getEngine().load(htmlPath);
        webView.getEngine().setUserStyleSheetLocation(cssPath);

        webView.getEngine().getLoadWorker().stateProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                loaded = true;
                changeMode();
                webView.getEngine().executeScript("configField(" + fieldRestriction + "," + fieldWidth + "," + webViewHeight + ")");
            }
        }));

        FlowPane.setMargin(editButton, new Insets(0, 0, 0, 5));
        editButton.setText("Редактировать");

        FlowPane.setMargin(saveButton, new Insets(0, 0, 0, 5));
        saveButton.setText("Сохранить");

        editButton.setOnAction(event -> onEdit());
        saveButton.setOnAction(event -> onSave());
    }
}
