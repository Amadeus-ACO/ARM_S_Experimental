package client.mainWindow.pages.labPages.labListPage.labDescription;

import client.mainWindow.MainController;
import client.mainWindow.pages.Pages;
import entity.work.GivenTask;
import entity.work.solution.Instrument;
import entity.work.solution.Step;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class LabDescriptionController extends LabDescriptionView implements Initializable {

    // TODO: Добавить общую таблицу в виде Р-графа (как таблица наследования) для описания проделанной работы по задаче
    //  Колонки: Шаг, Инструмент, Результат
    //  Объявление: Не пройдено входное тестирование
    //  Убрать кнопку "Reload Page"

    public static final String NULL_TEST = "Входное тестирование";
    public static final String ALGORITHM = "Алгоритм";
    public static final String FINAL_TEST = "Финальное тестирование";
    public static final String STEP_RESULT = "Результат шага";
    public static final String GRADE = "Оценка";
    public static final String START_DATE = "Дата выдачи";
    public static final String FINISH_DATE = "Дата завершения";

    public static final int STAGE_LEVEL = 1;
    public static final int STEP_LEVEL = 2;

    private final LabDescriptionModel labDescriptionModel = new LabDescriptionModel(this);

    @FXML
    VBox mainVBox;

    @FXML
    SplitPane mainSplitPane;

    @FXML
    TreeView<String> stepTreeView;

    @FXML
    TableView<Pair<String, String>> resultTableView;

    @FXML
    GridPane formulationProblemGridPane;

    @FXML
    WebView formulationProblemWebView;

    @FXML
    Label statusLabel;

    @FXML
    SplitPane progressSplitPane;

    @FXML
    GridPane labProgressGridPane;

    @FXML
    Label stepResultLabel;

    @FXML
    Label startDateLabel;

    @FXML
    Label finishDateLabel;

    @FXML
    HBox buttonPane;

    @FXML
    Button startLabButton;

    @FXML
    Button commentButton;

    @FXML
    Button TaskHelpBtn;
    WebView TaskHelpWebView;

    Label commentLabel = new Label();

    private boolean control_flag;
    private boolean stop_scrolling = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainVBox.setVisible(false);

        formulationProblemWebView.setOnDragDropped(event -> {});
        formulationProblemWebView.setOnDragOver(event -> {});
        formulationProblemWebView.setOnDragDetected(event -> {});
        formulationProblemWebView.setOnDragDone(event -> {});
        formulationProblemWebView.setOnDragEntered(event -> {});
        formulationProblemWebView.setOnDragExited(event -> {});
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

        stepTreeView.setRoot(new TreeItem<>("Этапы"));
        stepTreeView.setShowRoot(false);
        stepTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, treeItem) -> {
            resultTableView.getItems().clear();
            switch (stepTreeView.getTreeItemLevel(treeItem)) {
                case STAGE_LEVEL ->
                    labProgressGridPane.setVisible(false);
                case STEP_LEVEL -> {
                    labProgressGridPane.setVisible(true);
                    Step selectedStep = labDescriptionModel.getGivenTask().getAlgorithm().getStepList().stream()
                        .filter(step -> step.getStepName().getValue().equals(treeItem.getValue())).findFirst().get();

                    stepResultLabel.setText(STEP_RESULT + ": " + selectedStep.getResult());

                    for (Instrument instrument : selectedStep.getInstrumentList()) {
                        resultTableView.getItems().add(new Pair<>(instrument.getName(), instrument.getResult()));
                    }
                }
            }
        });

        TableColumn<Pair<String, String>, String> instrumentColumn = new TableColumn<>("Инструмент");
        TableColumn<Pair<String, String>, String> resultColumn = new TableColumn<>("Результат");
        instrumentColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        resultTableView.getColumns().add(instrumentColumn);
        resultTableView.getColumns().add(resultColumn);
        resultTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        /*resultColumn.setCellFactory(col -> {
            TableCell<String, MathField> cell = new TableCell<>();
            for
            cell.setItem();
        });*/

        startLabButton.setOnAction(e -> MainController.getInstance().requestOpenPage(Pages.LAB_PAGE));


        TaskHelpWebView = new WebView();
        commentButton.setOnAction(event -> {
            Stage stage = new Stage();
            AnchorPane pane = new AnchorPane();
            stage.setScene(new Scene(pane, 320, 240));
            pane.getChildren().add(commentLabel);

            AnchorPane.setLeftAnchor(commentLabel,5.0);
            AnchorPane.setTopAnchor(commentLabel,5.0);

            stage.setTitle("Комментарий");
            commentLabel.setText(labDescriptionModel.getGivenTask().getComment());

            stage.show();
        });

        TaskHelpBtn.setOnAction(event -> {
            Stage stage = new Stage();
            AnchorPane pane = new AnchorPane();
            stage.setScene(new Scene(pane, 800, 600));
            pane.getChildren().add(TaskHelpWebView);

            AnchorPane.setBottomAnchor(TaskHelpWebView, 0.0);
            AnchorPane.setLeftAnchor(TaskHelpWebView, 0.0);
            AnchorPane.setRightAnchor(TaskHelpWebView, 0.0);
            AnchorPane.setTopAnchor(TaskHelpWebView, 0.0);

            TaskHelpWebView.getEngine().load(new File("resources/metod1.html").toURI().toString());

            stage.setTitle("Теоретическая справка");

            stage.show();
        });
        // Инициализация отображения
        super.init();
    }

    @FXML
    public void onLabExecutionButton(ActionEvent actionEvent) {

    }

    public void onKeyPressedFormulationProblem(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL) {
            formulationProblemWebView.getEngine().executeScript(
                "document.body.classList.add('stop-scrolling')"
            );

        }
        System.out.println(keyEvent.getCode());
    }
    public void onKeyReleasedFormulationProblem(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.CONTROL) {
            System.out.println("control_flag = false");
            formulationProblemWebView.getEngine().executeScript(
                    "document.body.classList.remove('stop-scrolling')"
            );
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
            System.out.println("Scroll " + formulationProblemWebView.getZoom());
        }
    }

    public void update(GivenTask givenTask) {
        if (givenTask == null) {
            updateView(null);
            return;
        }

        // Model
        labDescriptionModel.update(givenTask);

        // Controller
        resetDefaults();
        if (givenTask.getCompleteStatus() == GivenTask.COMPLETE_STATUS.WAITING_FOR_CHECK ||
            givenTask.getCompleteStatus() == GivenTask.COMPLETE_STATUS.WAITING_FOR_FINAL_TEST_CHECK) {

        } else {
            formulationProblemWebView.getEngine().loadContent(givenTask.getTaskVariant().getTask().getText());
            stepTreeView.getRoot().getChildren().add(new TreeItem<>(NULL_TEST));
            if (givenTask.getCompleteStatus() == GivenTask.COMPLETE_STATUS.IN_WORK) {
                TreeItem<String> algorithmTreeItem = new TreeItem<>(ALGORITHM);
                stepTreeView.getRoot().getChildren().add(algorithmTreeItem);
                for (Step step : givenTask.getAlgorithm().getStepList()) {
                    TreeItem<String> stepTreeItem = new TreeItem<>(step.getStepName().getValue());
                    algorithmTreeItem.getChildren().add(stepTreeItem);
                }
            } else if (givenTask.getCompleteStatus() == GivenTask.COMPLETE_STATUS.COMPLETED) {
                finishDateLabel.setText(FINISH_DATE + ": " +
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(givenTask.getFinishDate()));
                gradeLabel.setText(GRADE + ": " + givenTask.getGrade());
            }
        }
        startDateLabel.setText(START_DATE + ": " +
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(givenTask.getStartDate()));
        statusLabel.setText(labDescriptionModel.getGivenTask().getCompleteStatus().getValue());

        // View
        updateView(givenTask.getCompleteStatus());
    }

    private void resetDefaults() {
        formulationProblemWebView.getEngine().load(new File("resources/task1.html").toURI().toString());
        stepTreeView.getRoot().getChildren().clear();
    }
}


        /*resultTableView.getColumns().add(instrumentColumn);
        resultTableView.getColumns().add(resultColumn);
        resultTableView.getItems().add(new Pair<>("1. Сложение в столбик" , "0000111"));
        resultTableView.getItems().add(new Pair<>("2. Вычитание в столбик", "0000110"));
        resultTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);*/
//            formulationProblemWebView.getEngine().executeScript(
//                    "scrollTop = window.pageYOffset || document.documentElement.scrollTop; " +
//                    "scrollLeft = window.pageXOffset || document.documentElement.scrollLeft, " +
//                    "window.onscroll = function() { " +
//                            "window.scrollTo(scrollLeft, scrollTop); " +
//                    "};"
//            );
//formulationProblemWebView.getEngine().executeScript("console.log(window.scrollY)");
//Integer barXPos = (Integer) formulationProblemWebView.getEngine().executeScript("document.body.scrollLeft");
//Integer barYPos = (Integer) formulationProblemWebView.getEngine().executeScript("document.body.scrollTop");
//formulationProblemWebView.getEngine().executeScript("window.scrollTo(" + barXPos + ", " + barYPos + ")");
//            formulationProblemWebView.getEngine().executeScript(
//                    "window.onscroll = function() {};"
//            );
/*if (!stop_scrolling) {
    stop_scrolling = true;
    System.out.println((String) formulationProblemWebView.getEngine().executeScript("document.documentElement.outerHTML"));
}*/

    /*void onMouseEnteredFormulationProblem() {

    }
    void onScaleFormulationProblem() {

    }
    void requestScaleFormulationProblem() {
        labDescriptionView.changeScaleFormulationProblem();
    }*/

//formulationProblemLabel.setText("_Постановка задачи");
//formulationProblemLabel.setMnemonicParsing(true);
//formulationProblemLabel.setLabelFor(startLabButton);
        /*stepTreeView.getRoot().getChildren().add(new TreeItem<>("Входное тестирование"));
        stepTreeView.getRoot().getChildren().add(new TreeItem<>("Алгоритм"));
        stepTreeView.getRoot().getChildren().get(1).getChildren().add(new TreeItem<>("1. Вычисление выражения A2–B2 в обратном коде."));
        stepTreeView.getRoot().getChildren().get(1).getChildren().add(new TreeItem<>("2. Вычисление выражения A2–B2 в дополнительном коде."));
        stepTreeView.getRoot().getChildren().add(new TreeItem<>("Финальное тестирование"));*/
//TODO: Подумать над подстройкой масштаба под разрешение окна