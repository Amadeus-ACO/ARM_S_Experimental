package client.mainWindow.tabPane;

import client.mainWindow.tab.TabController;
import com.google.gson.JsonObject;
import javafx.scene.Node;
import javafx.scene.Parent;
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
        tabPane.addEventHandler(Tab.TAB_CLOSE_REQUEST_EVENT, event -> onCloseTab());

        // Инициаилизация панели в классе отображения
        tabPaneView.initTabPane(tabPane);

    }


    // Переписать под менеджер панелей
    private void onCloseTab() {
        System.out.println("BRAAT");

        // На панели одна вкладка, и она - удаляемая
        if (tabPane.getTabs().size() == 1) {

            System.out.println("ZDAROVA");
            Parent parentPane = tabPane.getParent().getParent();
            System.out.println(parentPane);
            System.out.println(parentPane.getChildrenUnmodifiable().size());
            System.out.println(parentPane.getParent().getChildrenUnmodifiable().size());

            // Родительская панель - панель-разделитель,
            // и у неё две подчинённые панели (включая удаляемую)
            if (parentPane instanceof SplitPane &&
                    ((SplitPane) parentPane).getItems().size() == 2) {
                SplitPane splitPane = (SplitPane) parentPane;

                // Удаление панели вкладок
                splitPane.getItems().remove(tabPane);

                Node lastChild = splitPane.getItems().get(0);

                System.out.println("YA VOOBSHE ZDES");

                parentPane = splitPane.getParent().getParent();

                // Панель, в которой находится родительская панель,
                // является панелью класса SplitPane
                if (parentPane instanceof SplitPane) {
                    System.out.println("YA SPLITPANE NACHALO");
                    SplitPane rootPane = (SplitPane) parentPane;
                    int index = rootPane.getItems().indexOf(splitPane);
                    rootPane.getItems().add(index, lastChild);
                    rootPane.getItems().remove(splitPane);
                    System.out.println("YA SPLITPANE KONEC");
                }

                // Панель, в которой находится родительская панель,
                // является панелью класса AnchorPane
                else if (parentPane instanceof AnchorPane) {
                    System.out.println("YA ANCHORPANE");
                    AnchorPane rootPane = (AnchorPane) parentPane;
                    rootPane.getChildren().remove(splitPane);
                    rootPane.getChildren().add(lastChild);
                }
            }
            else if (tabPane.getParent() instanceof AnchorPane) {
               // Разное событие для разных менеджеров
                // ((AnchorPane) tabPane.getParent()).getChildren().remove(tabPane);
            }
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
