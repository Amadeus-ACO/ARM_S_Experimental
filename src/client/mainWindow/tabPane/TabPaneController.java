package client.mainWindow.tabPane;

import client.mainWindow.pages.Pages;
import client.mainWindow.tab.TabController;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class TabPaneController {
    private final TabPaneModel tabPaneModel = new TabPaneModel(this);
    private final TabPaneView tabPaneView = new TabPaneView(this);

    private TabPane tabPane;

    public TabPaneController () {

        // Создание панели вкладок
        tabPane = new TabPane();
        tabPane.addEventHandler(Tab.TAB_CLOSE_REQUEST_EVENT, event -> onCloseTab(event));

        // Инициаилизация панели в классе отображения
        tabPaneView.initTabPane(tabPane);

    }


    // Переписать под менеджер панелей
    private void onCloseTab(Event event) {

        // На панели одна вкладка, и она - удаляемая
        if (tabPane.getTabs().size() == 1) {
            requestOpenPage(Pages.MAIN_PAGE);
        }


    }

    public void requestLoadConfig(JsonObject config) {
        tabPaneModel.loadConfig(config);
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void requestCreateTab(JsonObject config) {
        TabController tabController = new TabController();
        tabController.requestLoadConfig(config);
        tabPane.getTabs().add(tabController.getTab());
    }

    public void requestOpenPage(String pageType, String... params) {
        TabController tabController = new TabController();
        tabController.requestOpenPage(pageType, params);

        tabPane.getTabs().add(tabController.getTab());
        tabPane.getSelectionModel().select(tabController.getTab());
    }

    public void requestSetBounds(JsonObject bounds) {
        tabPaneView.setBounds(bounds.get("width").getAsInt(),
                              bounds.get("height").getAsInt()
        );
    }
    public void requestSetGridLocation(JsonObject config) {
        tabPaneView.setGridLocation(config.get("row").getAsInt(),
                                    config.get("column").getAsInt(),
                                    config.get("rowSpan").getAsInt(),
                                    config.get("columnSpan").getAsInt()
        );
    }



}
