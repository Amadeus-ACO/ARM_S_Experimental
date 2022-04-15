package client.mainWindow.tab;

import client.mainWindow.tabPane.TabPaneController;
import client.mainWindow.tabPane.TabPaneModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class TabModel {
    //private ArrayList<TabPaneModel> TabPaneModelList;
//    public ArrayList<TabPaneModel> getTabPaneModelList() {
//        return TabPaneModelList;
//    }
    private TabController tabController;

    public TabModel(TabController tabController) {
        // Обновление конфигурации пользователя
        // Config.getUserData()
        this.tabController = tabController;
    }

    void loadConfig(JsonObject config) {
        JsonObject pageConfig = config.get("page").getAsJsonObject();
        tabController.requestOpenPage(pageConfig);

    }


}
