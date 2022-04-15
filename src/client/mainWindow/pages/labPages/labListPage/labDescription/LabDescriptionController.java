package client.mainWindow.pages.labPages.labListPage.labDescription;

import client.mainWindow.MainController;
import client.mainWindow.pages.Pages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.util.Pair;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LabDescriptionController implements Initializable {

    // TODO: Добавить общую таблицу в виде Р-графа (как таблица наследования) для описания проделанной работы по задаче
    //  Колонки: Шаг, Инструмент, Результат
    //  Объявление: Не пройдено входное тестирование
    //  Убрать кнопку "Reload Page"

    LabDescriptionModel labDescriptionModel = new LabDescriptionModel(this);
    LabDescriptionView labDescriptionView = new LabDescriptionView(this);

    @FXML
    TreeView<String> stepTreeView;

    @FXML
    TableView<Pair<String, String>> resultTableView;

    @FXML
    Button startLabButton;

    @FXML
    GridPane formulationProblemGridPane;

    @FXML
    WebView formulationProblemWebView;

    @FXML
    Label formulationProblemLabel;

    private boolean control_flag;
    private boolean stop_scrolling = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        formulationProblemWebView.getEngine().load(new File("resources/lect/Lect1.html").toURI().toString());
        formulationProblemWebView.getEngine().getLoadWorker().stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    formulationProblemWebView.getEngine().executeScript(
                            "var style = document.createElement('style');" +
                            "style.type = 'text/css';" +
                            "style.innerHTML = '.stop-scrolling {height: 100%; overflow: hidden;}';" +
                            "document.getElementsByTagName('head')[0].appendChild(style);"
                    );
                }
        );
        //TODO: Подумать над подстройкой масштаба под разрешение окна
        formulationProblemLabel.setText("_Постановка задачи");
        formulationProblemLabel.setMnemonicParsing(true);
        formulationProblemLabel.setLabelFor(startLabButton);

        stepTreeView.setRoot(new TreeItem<>("Этапы"));
        stepTreeView.setShowRoot(false);

        stepTreeView.getRoot().getChildren().add(new TreeItem<>("Входное тестирование"));
        stepTreeView.getRoot().getChildren().add(new TreeItem<>("Алгоритм"));
        stepTreeView.getRoot().getChildren().get(1).getChildren().add(new TreeItem<>("1. Вычисление выражения A2–B2 в обратном коде."));
        stepTreeView.getRoot().getChildren().get(1).getChildren().add(new TreeItem<>("2. Вычисление выражения A2–B2 в дополнительном коде."));
        stepTreeView.getRoot().getChildren().add(new TreeItem<>("Финальное тестирование"));

        TableColumn<Pair<String, String>, String> instrumentColumn = new TableColumn<>("Инструмент");
        TableColumn<Pair<String, String>, String> resultColumn = new TableColumn<>("Результат");
        instrumentColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        resultTableView.getColumns().add(instrumentColumn);
        resultTableView.getColumns().add(resultColumn);
        resultTableView.getItems().add(new Pair<>("1. Сложение в столбик" , "0000111"));
        resultTableView.getItems().add(new Pair<>("2. Вычитание в столбик", "0000110"));
        resultTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        startLabButton.setOnAction(e -> MainController.getInstance().requestOpenPage(Pages.LAB_PAGE));
    }

    void onMouseEnteredFormulationProblem() {

    }

    void onScaleFormulationProblem() {

    }

    void requestScaleFormulationProblem() {
        labDescriptionView.changeScaleFormulationProblem();
    }

    public void onKeyPressedFormulationProblem(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL) {
            /*if (!stop_scrolling) {
                stop_scrolling = true;
                System.out.println((String) formulationProblemWebView.getEngine().executeScript("document.documentElement.outerHTML"));
            }*/
            formulationProblemWebView.getEngine().executeScript(
                "document.body.classList.add('stop-scrolling')"
            );
//            formulationProblemWebView.getEngine().executeScript(
//                    "scrollTop = window.pageYOffset || document.documentElement.scrollTop; " +
//                    "scrollLeft = window.pageXOffset || document.documentElement.scrollLeft, " +
//                    "window.onscroll = function() { " +
//                            "window.scrollTo(scrollLeft, scrollTop); " +
//                    "};"
//            );
        }
        System.out.println(keyEvent.getCode());
    }
    public void onKeyReleasedFormulationProblem(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL) {
            System.out.println("control_flag = false");
            formulationProblemWebView.getEngine().executeScript(
                    "document.body.classList.remove('stop-scrolling')"
            );
//            formulationProblemWebView.getEngine().executeScript(
//                    "window.onscroll = function() {};"
//            );
        }
        System.out.println(keyEvent.getCode());
    }
    public void onScrollStartedFormulationProblem(ScrollEvent scrollEvent) {
        System.out.println("scrollStarted");
    }
    public void onScrollFinishedFormulationProblem(ScrollEvent scrollEvent) {
        System.out.println("scrollFinished");
    }
    public void onScrollFormulationProblem(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            double zoom;
            if (scrollEvent.getDeltaY() > 0 ) {
                zoom = formulationProblemWebView.getZoom() + 0.05;
                if (zoom <= 1.6) formulationProblemWebView.setZoom(zoom);
                //formulationProblemWebView.setZoom(Math.min(zoom, 1.6));
            }
            else {
                zoom = formulationProblemWebView.getZoom() - 0.05;
                if (zoom >= 0.2) formulationProblemWebView.setZoom(zoom);
                //formulationProblemWebView.setZoom(Math.max(zoom, 0.2));
            }
            scrollEvent.consume();
            //formulationProblemWebView.getEngine().executeScript("console.log(window.scrollY)");
            //Integer barXPos = (Integer) formulationProblemWebView.getEngine().executeScript("document.body.scrollLeft");
            //Integer barYPos = (Integer) formulationProblemWebView.getEngine().executeScript("document.body.scrollTop");
            //formulationProblemWebView.getEngine().executeScript("window.scrollTo(" + barXPos + ", " + barYPos + ")");
            System.out.println("Scroll " + formulationProblemWebView.getZoom());
        }
    }
}
