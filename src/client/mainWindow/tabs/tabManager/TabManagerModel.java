package client.mainWindow.tabManager;

import client.mainWindow.tabPane.TabPaneModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class TabManagerModel {

    private TabManagerController tabManagerController;
    private ArrayList<TabPaneModel> tabPaneModelList;

    TabManagerModel(TabManagerController tabManagerController) {
        this.tabManagerController = tabManagerController;
    }

    void parseConfig(JsonObject config) {

        // Верификация JSON

        // Запрос на установку параметров таблицы
        tabManagerController.requestConfigureGridPane(config.getAsJsonObject("grid"));

        // Инициация запросов на создание панелей вкладок (TabPane)
        JsonArray tabPaneList = config.getAsJsonArray("tabPaneList");
        for (JsonElement elem: tabPaneList) {
            tabManagerController.requestCreateTabPane((JsonObject) elem);
        }
    }
}
