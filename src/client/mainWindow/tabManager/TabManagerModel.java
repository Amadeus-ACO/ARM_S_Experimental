package client.mainWindow.tabManager;

import client.mainWindow.tabPane.TabPaneModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.scene.control.SplitPane;
import javafx.util.Pair;

import java.util.ArrayList;

public class TabManagerModel {

    private TabManagerController tabManagerController;
    private ArrayList<TabPaneModel> tabPaneModelList;

    private int splitPaneID, tabPaneID;
    private String generateSplitPaneID() {return "SplitPane-"+(splitPaneID++); }
    private String generateTabPaneID() { return "TabPane-"+(tabPaneID++); }

    TabManagerModel(TabManagerController tabManagerController) {
        this.tabManagerController = tabManagerController;
    }

    void loadConfig(JsonObject config) {

        // Запрос на установку размеров менеджера
        tabManagerController.requestSetBounds(config.getAsJsonObject("bounds"));

        // Загрузка панели
        loadPaneConfig(config, "anchorPane");

        /*// Инициация запросов на создание панелей вкладок (TabPane)
        JsonArray tabPaneList = config.getAsJsonArray("tabPaneList");
        for (JsonElement elem: tabPaneList) {
            tabManagerController.requestCreateTabPane(elem.getAsJsonObject());
        }*/
    }

    /**
     * Загрузка конфигурации панели со SplitPane и TabPane
     * @param config - конфигурация текущей панели
     */
    void loadPaneConfig(JsonObject config, String parentPaneID) {
        String currentPaneID;
        String paneType = config.get("paneType").getAsString();
        if (paneType.equals(TabPaneModel.HORIZONTAL_SPLIT_PANE)
                || paneType.equals(TabPaneModel.VERTICAL_SPLIT_PANE)) {

            // Запрос на создание SplitPane
            currentPaneID = tabManagerController.requestCreateSplitPane(parentPaneID, paneType).getKey();

            // Загрузка конфигураций для вложенных панелей
            JsonArray paneList = config.get("paneList").getAsJsonArray();
            for (JsonElement elem: paneList) {

                // Рекурсивный вызов
                loadPaneConfig(elem.getAsJsonObject(), currentPaneID);
            }
        }
        else if (paneType.equals(TabPaneModel.TAB_PANE)) {

            System.out.println("parentPaneID: " + parentPaneID);

            // Запрос на создание TabPane
            tabManagerController.requestCreateTabPane(parentPaneID, config);
        }
    }

    /**
     * Метод создания модели панели-разделителя
     */
    String createSplitPaneModel() {
        return generateSplitPaneID();
    }

    /**
     * Метод создания панели с вкладками
     */
    String createTabPaneModel() {
        return generateTabPaneID();
    }
}
