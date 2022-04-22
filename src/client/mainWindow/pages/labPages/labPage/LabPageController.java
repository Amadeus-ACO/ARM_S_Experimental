package client.mainWindow.pages.labPages.labPage;

import client.mainWindow.pages.labPages.labPage.algorithm.AlgorithmController;
import client.mainWindow.pages.labPages.labPage.base.stage.Stages;
import client.mainWindow.pages.labPages.labPage.finalTest.FinalTestController;
import client.mainWindow.pages.labPages.base.formulationTask.FormulationTaskController;
import client.mainWindow.pages.labPages.labPage.base.stage.StageButton;
import client.mainWindow.pages.labPages.labPage.nullTest.NullTestController;

import com.google.gson.JsonObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class LabPageController {

    private LabPageModel labPageModel = new LabPageModel(this);
    private LabPageView labPageView = new LabPageView(this);

    private ScrollPane root;
    private VBox stagePane;
    private GridPane stageTitleButtonGrid;

    private String currentStageType = "";

    public Region getRoot() {
        return stagePane;
    }

    public LabPageController() {
        root = new ScrollPane();
        labPageView.setRoot(root);

        stagePane = new VBox();
        labPageView.setStagePane(stagePane);

        stageTitleButtonGrid = new GridPane();
        labPageView.setStageTitleButtonGrid(stageTitleButtonGrid);

        FormulationTaskController formulationTaskController = new FormulationTaskController();
        StageButton stageButton = new StageButton("Постановка задачи", root);
        stageButton.setOnAction(event -> onStageButtonAction(Stages.FORMULATION_PROBLEM, formulationTaskController.getRoot()));
        labPageView.addStageButton(stageButton);

        NullTestController nullTestController = new NullTestController();
        stageButton = new StageButton("Входное тестирование", root);
        stageButton.setOnAction(event -> onStageButtonAction(Stages.NULL_TEST, nullTestController.getRoot()));
        labPageView.addStageButton(stageButton);

        AlgorithmController algorithmController = new AlgorithmController();
        stageButton = new StageButton("Алгоритм", root);
        stageButton.setOnAction(event -> onStageButtonAction(Stages.ALGORITHM, algorithmController.getRoot()));
        labPageView.addStageButton(stageButton);

        FinalTestController finalTestController = new FinalTestController();
        stageButton = new StageButton("Финальное тестирование", root);
        stageButton.setOnAction(event -> onStageButtonAction(Stages.FINAL_TEST, finalTestController.getRoot()));
        labPageView.addStageButton(stageButton);

        stageButton = new StageButton("Отчет", root);
        stageButton.setOnAction(event -> {
            try {
                onStageButtonAction(Stages.REPORT, FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main_window/pages/lab_list_page/labReportTab.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        labPageView.addStageButton(stageButton);

        onStageButtonAction(Stages.FORMULATION_PROBLEM, formulationTaskController.getRoot());

    }

    /**
     * Добавление этапа к лабораторной работе
     *
     * @param name - название этапа на естественном языке
     * @param type - тип этапа
     * @param passed - флаг, отражающий пройденность этапа (пройден или нет)
     * @param config - конфигурация этапа
     */
    void requestAddNewStage(String name, String type,
                            boolean passed, JsonObject config) {

        StageButton stageButton;
        Region stageRoot;
        Parent root = null;
        switch (type) {
            // Постановка задачи
            case Stages.FORMULATION_PROBLEM -> {
                FormulationTaskController formulationTaskController = new FormulationTaskController();
                root = formulationTaskController.getRoot();
            }
            // Входное тестирование
            case Stages.NULL_TEST -> {
                NullTestController nullTestController = new NullTestController();
                root = nullTestController.getRoot();
            }
            // Алгоритм
            case Stages.ALGORITHM -> {
                AlgorithmController algorithmController = new AlgorithmController();
                root = algorithmController.getRoot();
            }
            // Реализация
            /*
            case Stages.REALIZATION -> {
                RealizationController realizationController = new RealizationController(config);
                stageRoot = realizationController.getRoot();
            }

             */
            // Финальное тестирование
            case Stages.FINAL_TEST -> {
                FinalTestController finalTestController = new FinalTestController();
                root = finalTestController.getRoot();
            }
            // Отчёт
            case Stages.REPORT -> {
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main_window/pages/lab_list_page/labReportTab.fxml")));;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            default -> {
                // TODO: Обработка ситуации, когда нельзя добавить этап
                return;
            }
        }

       // stageButton = new StageButton(name, passed, root);
       //Parent finalRoot = root;
        //stageButton.setOnAction(event -> onStageButtonAction(type, finalRoot));
       // labPageView.addStageButton(stageButton);

        // TODO: Перенести StageButton в контроллер
        // TODO: Подумать над базовым классом для контроллеров и использованием ScrollPane/AnchorPane
        // TODO: Реализовать проверку на то, что соответствующая
        //       панель уже стоит
        // TODO: Подумать над предварительной загрузкой контроллеров
        //       (чтобы их не нужно было грузить заново)
        // TODO: Изменение констант
        // TODO: Drag_and_Drop для любого раздела

        if (currentStageType.isEmpty())
            // TODO: Какой этап открывать?
            stageTitleButtonGrid.getChildren().get(0).fireEvent(new ActionEvent());
    }

    public void onStageButtonAction(String type, Parent root) {
        if (!currentStageType.equals(type)) {
            if (!currentStageType.isEmpty()) {
                stagePane.getChildren().remove(1);
            }
            currentStageType = type;
            stagePane.getChildren().add(root);
        }
    }
}
