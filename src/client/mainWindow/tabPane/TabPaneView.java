package client.mainWindow.tabPane;

import com.google.gson.JsonObject;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;

public class TabPaneView {

    private TabPaneController tabPaneController;

    private TabPane tabPane;
    void initTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
    TabPane getTabPane() {
        return tabPane;
    }

    TabPaneView(TabPaneController tabPaneController) {
        this.tabPaneController = tabPaneController;
    }


    void setGridLocation(int row, int column, int rowSpan, int columnSpan) {
        GridPane.setConstraints(tabPane, column, row, rowSpan, columnSpan);
        GridPane.setFillWidth(tabPane, true);
        GridPane.setFillHeight(tabPane, true);
    }

    public void setBounds(int width, int height) {
        tabPane.setPrefWidth(width);
        tabPane.setPrefHeight(height);
    }
}
