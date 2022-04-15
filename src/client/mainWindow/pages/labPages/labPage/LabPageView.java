package client.mainWindow.pages.labPages.labPage;

import client.mainWindow.pages.labPages.labPage.base.stage.StageButton;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LabPageView {

    private LabPageController labPageController;

    private ScrollPane stageRoot;
    private VBox stagePane;
    private GridPane stageTitleButtonGrid;

    LabPageView(LabPageController labPageController) {
        this.labPageController = labPageController;
    }

    public void setRoot(ScrollPane stageRoot) {
        this.stageRoot = stageRoot;
        stageRoot.setFitToWidth(true);
    }

    public void setStagePane(VBox stagePane) {
        this.stagePane = stagePane;
        stagePane.setFillWidth(true);
        stagePane.setMaxWidth(Region.USE_COMPUTED_SIZE);
        stagePane.setMaxHeight(Region.USE_COMPUTED_SIZE);
        stageRoot.setContent(stagePane);

    }

    public void setStageTitleButtonGrid(GridPane stageTitleButtonGrid) {
        this.stageTitleButtonGrid = stageTitleButtonGrid;
        stageTitleButtonGrid.setMaxWidth(Region.USE_COMPUTED_SIZE);
        stageTitleButtonGrid.setMaxHeight(Region.USE_COMPUTED_SIZE);
        stagePane.getChildren().add(stageTitleButtonGrid);
    }

    void addStageButton(StageButton stageButton) {
        addStageButton(stageButton, false);
    }

    void addStageButton(StageButton stageButton, boolean passed) {
        // Добавление кнопки этапа
        stageTitleButtonGrid.getColumnConstraints().add(new ColumnConstraints());
        stageTitleButtonGrid.add(stageButton, stageTitleButtonGrid.getColumnCount()-1, 0);

        // Установка форматирования кнопки в зависимости от пройденности этапа
        if (passed) // Этап пройден
            stageButton.setStyle("-fx-border-color: green; -fx-border-width: 1");

        // Изменение разметки таблицы кнопок
        changeGridPercent();
    }

    void changeGridPercent() {
        ObservableList<ColumnConstraints> columnList = stageTitleButtonGrid.getColumnConstraints();
        columnList.forEach(column -> column.setPercentWidth(100.0/columnList.size()));
        System.out.println("100/" + columnList.size()  + " = " + 100.0 / columnList.size());
    }


}
