package client.mainWindow.tabPane;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.scene.layout.GridPane;

public class TabPaneModel {

    public static final String HORIZONTAL_SPLIT_PANE = "HorizontalSplitPane";
    public static final String VERTICAL_SPLIT_PANE = "VerticalSplitPane";
    public static final String TAB_PANE = "TabPane";

    private TabPaneController tabPaneController;

    TabPaneModel(TabPaneController tabPaneController) {
        this.tabPaneController = tabPaneController;
    }

    void loadConfig(JsonObject config) {
        tabPaneController.requestSetBounds(config.getAsJsonObject("bounds"));

        // Инициация запросов на создание панелей вкладок (TabPane)
        JsonArray tabList = config.getAsJsonArray("tabList");
        for (JsonElement elem: tabList) {
            tabPaneController.requestCreateTab((JsonObject) elem);
        }
    }
}
