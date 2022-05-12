package client.mainWindow.pages.labPages.base.formulationTask;

import com.google.gson.JsonObject;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

public class FormulationTaskController {
    // TODO: StageController???
    //   Объект одного и того же класса, как этап и как отображение в описании задачи
    //   Доразделить Controller и View

    private FormulationTaskModel formulationTaskModel = new FormulationTaskModel(this);
    private FormulationTaskView formulationTaskView = new FormulationTaskView(this);

    private WebView webView;
    private GridPane root;
    private WebView TaskHelpWebView;

    public GridPane getRoot() {
        return root;
    }

    public FormulationTaskController() {

        TaskHelpWebView = new WebView();

        // Кнопка "Теоретическая справка по заданию"
        Button readHelpButton = new Button("Теоретическая справка по заданию");
        readHelpButton.setOnAction(event -> {
            Stage stage = new Stage();

            AnchorPane pane = new AnchorPane();
            stage.setScene(new Scene(pane, 800, 600));
            pane.getChildren().add(TaskHelpWebView);

            AnchorPane.setBottomAnchor(TaskHelpWebView,0.0);
            AnchorPane.setLeftAnchor(TaskHelpWebView,0.0);
            AnchorPane.setRightAnchor(TaskHelpWebView,0.0);
            AnchorPane.setTopAnchor(TaskHelpWebView,0.0);

            TaskHelpWebView.getEngine().load(new File("resources/metod1.html").toURI().toString());

            stage.setTitle("Теоретическая справка");

            stage.show();
        });
        // TODO: Добавить, кагда появится раздел справки
        //       readHelpButton.setOnAction();

        // Браузер для отображения постановки задачи в формате HTML
        webView = new WebView();
        webView.getEngine().load(new File("resources/task1.html").toURI().toString());
        webView.getEngine().getLoadWorker().stateProperty().addListener(
            (observable, oldValue, newValue) -> webView.getEngine().executeScript(
                "var style = document.createElement('style');" +
                "style.type = 'text/css';" +
                "style.innerHTML = '.stop-scrolling {height: 100%; overflow: hidden;}';" +
                "document.getElementsByTagName('head')[0].appendChild(style);"
            )
        );
        webView.setOnScroll(this::onScrollFormulationTask);
        webView.setOnKeyPressed(this::onKeyPressedFormulationTask);
        webView.setOnKeyReleased(this::onKeyReleasedFormulationTask);
        webView.setOnDragDropped(event -> {});
        webView.setOnDragOver(event -> {});
        webView.setOnDragDetected(event -> {});
        webView.setOnDragDone(event -> {});
        webView.setOnDragEntered(event -> {});
        webView.setOnDragExited(event -> {});

        // Таблица для группировки элементов в постановке задачи
        root = new GridPane();


        // Настройка отображения постановки задачи
        formulationTaskView.init(root, readHelpButton, webView);

        webView.requestFocus();

        // TODO: В один класс постановку задачи + вариант

    }

    public void onKeyPressedFormulationTask(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL)
            webView.getEngine().executeScript(
                    "document.body.classList.add('stop-scrolling')"
            );
    }
    public void onKeyReleasedFormulationTask(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL)
            webView.getEngine().executeScript(
                    "document.body.classList.remove('stop-scrolling')"
            );
    }

    public void onScrollFormulationTask(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            double zoom;
            if (scrollEvent.getDeltaY() > 0 ) {
                zoom = webView.getZoom() + 0.05;
                if (zoom <= 1.6) webView.setZoom(zoom);
            }
            else {
                zoom = webView.getZoom() - 0.05;
                if (zoom >= 0.2) webView.setZoom(zoom);
            }
            scrollEvent.consume();
        }
    }

}
