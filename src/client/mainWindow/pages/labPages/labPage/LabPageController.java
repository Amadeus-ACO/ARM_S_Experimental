package client.mainWindow.pages.labPages.labPage;

import client.mainWindow.pages.labPages.labPage.algorithm.AlgorithmController;
import client.mainWindow.pages.labPages.labPage.base.stage.Stages;
import client.mainWindow.pages.labPages.labPage.finalTest.FinalTestController;
import client.mainWindow.pages.labPages.base.formulationTask.FormulationTaskController;
import client.mainWindow.pages.labPages.labPage.base.stage.StageButton;
import client.mainWindow.pages.labPages.labPage.nullTest.NullTestController;
import client.mainWindow.pages.labPages.labPage.report.ReportController;
import client.mainWindow.pages.labPages.labPage.realization.RealizationController;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

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

    public LabPageController(JsonObject config) {
        root = new ScrollPane();
        labPageView.setRoot(root);

        stagePane = new VBox();
        labPageView.setStagePane(stagePane);

        stageTitleButtonGrid = new GridPane();
        labPageView.setStageTitleButtonGrid(stageTitleButtonGrid);

        labPageModel.loadConfig(config);
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
        StageButton stageButton; Region stageRoot;
        switch (type) {
            // Постановка задачи
            case Stages.FORMULATION_PROBLEM -> {
                FormulationTaskController formulationTaskController = new FormulationTaskController(config);
                stageRoot = formulationTaskController.getRoot();
            }
            // Входное тестирование
            case Stages.NULL_TEST -> {
                NullTestController nullTestController = new NullTestController(config);
                stageRoot = nullTestController.getRoot();
            }
            // Алгоритм
            case Stages.ALGORITHM -> {
                AlgorithmController algorithmController = new AlgorithmController(config);
                stageRoot = algorithmController.getRoot();
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
                FinalTestController finalTestController = new FinalTestController(config);
                stageRoot = finalTestController.getRoot();
            }
            // Отчёт
            case Stages.REPORT -> {
                ReportController reportController = new ReportController(config);
                stageRoot = reportController.getRoot();
            }
            default -> {
                // TODO: Обработка ситуации, когда нельзя добавить этап
                return;
            }
        }

        stageButton = new StageButton(name, passed, stageRoot);
        stageButton.setOnAction(event -> onStageButtonAction(type, stageRoot));
        labPageView.addStageButton(stageButton);

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

    public void onStageButtonAction(String type, Region root) {
        if (!currentStageType.equals(type)) {
            if (!currentStageType.isEmpty()) {
                stagePane.getChildren().remove(1);
            }
            currentStageType = type;
            stagePane.getChildren().add(root);
        }
    }
}
