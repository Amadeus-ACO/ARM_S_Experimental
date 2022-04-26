package client.mainWindow;

import client.mainWindow.menuBar.MenuBarController;
import client.mainWindow.menuBar.MenuBarModel;
import client.mainWindow.menuBar.MenuBarView;
import client.mainWindow.roadMap.RoadMapController;
import client.mainWindow.roadMap.RoadMapModel;
import client.mainWindow.roadMap.RoadMapView;
import client.mainWindow.sectionMenu.SectionMenuController;
import client.mainWindow.tabManager.TabManagerController;
import client.mainWindow.tabManager.TabManagerView;
import client.mainWindow.tabPane.TabPaneController;
import client.mainWindow.widgetPanel.WidgetPanelController;
import client.mainWindow.widgetPanel.WidgetPanelModel;
import client.mainWindow.widgetPanel.WidgetPanelView;
import com.google.gson.JsonObject;
import entity.user.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import main.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class MainController extends MainView implements Initializable {

    // Реализация Singleton
    private static MainController instance;
    private MainController() {}
    public static MainController getInstance() {
        if (instance == null)
            instance = new MainController();
        return instance;
    }

    private final MainModel mainModel = new MainModel(this);
    private final MainView mainView = this;

    // Массив контроллеров вкладок
    private ArrayList<TabManagerController> tabManagerControllerList = new ArrayList<>();

    @FXML
    RoadMapController roadMapController;

    @FXML
    WidgetPanelController widgetPanelController;

    @FXML
    TabManagerController tabManagerController;

    @FXML
    SectionMenuController sectionMenuController;

    @FXML
    MenuBarController menuBarController;

    @FXML
    ScrollPane roadMapPane;

    private App app;

    public void setApp(App app) {
        this.app = app;
    }

    private JsonObject config;
    public void setConfig(JsonObject config) {
        this.config = config;
    }

    private TabPaneController focusedTabPaneController = null;
    public boolean isAnyPaneFocused() {return focusedTabPaneController != null;}
    public void requestChangeFocusedTabPaneController(TabPaneController tabPaneController) {
        // В фокусе оказалась другая панель
        if (focusedTabPaneController != tabPaneController) {
            if (focusedTabPaneController != null)
                focusedTabPaneController.getTabPane().setStyle("");
            focusedTabPaneController = tabPaneController;
            focusedTabPaneController.getTabPane().setStyle(
                    "-fx-border-color: fuchsia; -fx-border-style: solid; -fx-border-width: 1;"
            );
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /** -------------- Deprecated ---------------- */
        // System.out.println("hi");
        // RoadMap Initialization
        roadMapController.setRoadMapModel(new RoadMapModel());
        roadMapController.setRoadMapView(new RoadMapView());
        roadMapController.init(roadMapPane);

        // WidgetPanel Initialization
        widgetPanelController.setWidgetPanelModel(new WidgetPanelModel());
        widgetPanelController.setWidgetPanelView(new WidgetPanelView());

        // SectionMenu Initialization
        // sectionMenuController.setSectionMenuModel(new SectionMenuModel());
        // sectionMenuController.setSectionMenuView(new SectionMenuView());
        sectionMenuController.setMainController(this);

        // Инициализация контроллера главного менеджера панелей вкладок
        tabManagerController.setMainController(this);

        // MenuBar Initialization
        menuBarController.setMenuBarModel(new MenuBarModel());
        menuBarController.setMenuBarView(new MenuBarView());
        /** -------------- Deprecated ---------------- */

        // Инициализация основного менеджера панелей вкладок (объекта класса TabManagerController)
        // происходит перед выполнением этого метода, поскольку сначала вызываются методы initialize
        // для полей текущего объекта (tabManagerController - поле, относящееся к классу TabManagerController)

    }

    /* --------------------------------------------------- *
     * Запросы для работы с конфигурацией главной страницы *
     * --------------------------------------------------- */

    /**
     * Запрос на первоначальную загрузку конфигурации главной страницы
     *
     * @param config - конфигурация главной страницы
     */
    public void requestLoadConfig(JsonObject config) {
        mainModel.loadConfig(config);
    }

    /**
     * Запрос на сохранение конфигурации главной страницы
     *
     * @return JsonObject - текущая конфигурация главной страницы
     */
    public JsonObject requestSaveConfig() {
        return new JsonObject();
    }

    /* -------------- *
     * Другие запросы *
     * -------------- */

    /**
     * Запрос на изменение конфигурации
     * Главного менеджера панелей вкладок
     *
     * @param config - конфигурация менеджера
     */
    void requestSetConfigMainTabManager(JsonObject config) {
        tabManagerController.requestLoadConfig(config);
    }

    /**
     * Запрос на создание менеджера панелей вкладок
     *
     * 1. Инициализация контроллера (TabManagerController)
     * @see TabManagerController
     *
     * 2. Инициализация графического отображения (TabManagerView)
     * @see TabManagerView
     *
     * @param config - конфигурация менеджера панелей вкладок
     */
    void requestCreateTabManager(JsonObject config) {
        // Создание загрузчика FXML файла
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource(TabManagerController.FXML_PATH)
        );

        try {
            // Загрузка FXML файла
            loader.load();

            // Получение контроллера менеджера панелей вкладок из загрузчика FXML
            TabManagerController tabManagerController = loader.getController();

            // Сохранение ссылки на основной контроллер приложения в созданном контроллере
            tabManagerController.setMainController(this);

            // Запрос на изменение конфигурации менеджера
            tabManagerController.requestLoadConfig(config);

            // Добавление контроллера менеджера в список контроллеров
            tabManagerControllerList.add(loader.getController());

            // Вызов метода создания окна для менеджера
            mainView.createTabStage(config.getAsJsonObject("stage"), loader.getRoot());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запроос на открытие страницы
     * @param pageType - тип страницы
     */
    public void requestOpenPage(String pageType, String... params) {
        focusedTabPaneController.requestOpenPage(pageType, params);
    }

    public int getTabManagerCount() {
        return tabManagerControllerList.size();
    }

    public void requestUpdateModel(Student student) {
        mainModel.update(student);
        roadMapController.requestUpdate(Collections.emptyList());
    }

}
