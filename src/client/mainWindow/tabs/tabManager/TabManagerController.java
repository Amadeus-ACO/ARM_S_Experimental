package client.mainWindow.tabManager;

import client.mainWindow.tabPane.TabPaneController;
import com.google.gson.JsonObject;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TabManagerController extends TabManagerView implements Initializable {
    private final TabManagerModel tabManagerModel = new TabManagerModel(this);
    private final TabManagerView tabManagerView = this;
    public final static String FXML_PATH = "main_window/tabManager.fxml";

    /**
     * Инициаилизация менеджера панелей вкладок
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //createTabPane();
        //tabsZoneModel.getTabPaneModelList().get(0).createTabModel("Main");
        //tabsZoneModel.createTabModel(TabsZoneModel.MAIN_PAGE);
        //tabPane.getTabs().get(0).setGraphic(new Rectangle(16, 16, Color.RED));
        //addDragAndDropSupport(tabPane);
    }

    /**
     * Запрос на изменение конфигурации текущего менеджера панелей вкладок
     * @param config - конфигурация менеджера панелей вкладок
     */
    public void requestLoadConfig(JsonObject config) {
        tabManagerModel.parseConfig(config);
    }

    /**
     * Запрос на изменение таблицы, в которой отображаются панели вкладок
     * @param config - конфигурация таблицы
     */
    void requestConfigureGridPane(JsonObject config) {
        /*tabsManagerView.configGridPane(config.get("width").getAsDouble(),
                                       config.get("height").getAsDouble());*/
        tabManagerView.configGridPane(
                                       //config.get("rowSpan").getAsInt(),
                                       //config.get("columnSpan").getAsInt(),
                                       config.get("width").getAsDouble(),
                                       config.get("height").getAsDouble()
        );
    }

    /**
     * Запрос на создание панели вкладок
     * @param config - конфигурация панели вкладок
     */
    void requestCreateTabPane(JsonObject config) {

        // Создание контроллера панелей вкладок из загрузчика FXML
        TabPaneController tabPaneController = new TabPaneController();

        // Добавление панели вкладок в таблицу
        gridPane.add(tabPaneController.getTabPane(), 0, 0);

        // Запрос на загрузку конфигурации панели вкладов
        tabPaneController.requestLoadConfig(config);

        // Добавление контроллера менеджера в список контроллеров
        // tabManagerControllerList.add(loader.getController());
    }

    public void requestUpdateSeparators() {
        tabManagerView.updateSeparators();
    }
}
