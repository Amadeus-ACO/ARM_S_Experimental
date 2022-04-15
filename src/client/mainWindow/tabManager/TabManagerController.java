package client.mainWindow.tabManager;

import client.mainWindow.MainController;
import client.mainWindow.tabPane.TabPaneController;
import client.mainWindow.tabPane.TabPaneModel;
import com.google.gson.JsonObject;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TabManagerController extends TabManagerView implements Initializable {
    public final static String FXML_PATH = "main_window/tabManager.fxml";
    private static final Logger LOGGER = LoggerFactory.getLogger(TabManagerController.class);

    private final TabManagerModel tabManagerModel = new TabManagerModel(this);
    private final TabManagerView tabManagerView = this;

    private MainController mainController;

    private ArrayList<TabPaneController> tabPaneControllerList = new ArrayList<>();

    /**
     * Инициаилизация менеджера панелей вкладок
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    /**
     * Инициализация основного контроллера приложения
     * @param mainController - основной контроллер
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Запрос на изменение конфигурации текущего менеджера панелей вкладок
     * @param config - конфигурация менеджера панелей вкладок
     */
    public void requestLoadConfig(JsonObject config) {
        tabManagerModel.loadConfig(config);
    }

    /**
     * Запрос на изменение таблицы, в которой отображаются панели вкладок
     * @param config - конфигурация таблицы
     */
    void requestSetBounds(JsonObject config) {
        tabManagerView.setBounds(config.get("width").getAsDouble(),
                                 config.get("height").getAsDouble()
        );
    }

    /**
     * Запрос на создание панели-разделителя
     * (панель вкладок добавляется на панель, найденную по значению переданного идентификатора)
     *
     * На вход:
     *      @param parentPaneID - идентификатор родительской панели
     *      @param splitType - тип панели-разделителя
     *
     *  На выход:
     *      @return splitPaneID - идентификатор создаваемой панели-разделителя
     *              splitPane - создаваемая панель-разделитель
     */
    Pair<String, SplitPane> requestCreateSplitPane(String parentPaneID, String splitType) {
        if (parentPaneID.equals(""))
            return requestCreateSplitPane(((Parent) null), splitType);
        else if (parentPaneID.equals("anchorPane"))
            return requestCreateSplitPane(anchorPane, splitType);
        else
            return requestCreateSplitPane(findSplitPane(parentPaneID), splitType);
    }

    /**
     * Запрос на создание панели-разделителя
     * (панель вкладок добавляется на переданную в параметрах панель)
     *
     * На вход:
     *      @param parentPane - родительская панель
     *      @param splitType - тип панели-разделителя
     *
     *  На выход:
     *      @return splitPaneID - идентификатор создаваемой панели-разделителя
     *              splitPane - создаваемая панель-разделитель
     */
    Pair<String, SplitPane> requestCreateSplitPane(Parent parentPane, String splitType) {

        // Создание модели панели-разделителя
        String splitPaneID = tabManagerModel.createSplitPaneModel();

        // Создание панели-разделителя
        SplitPane splitPane = new SplitPane();

        // Установка ID для созданной панели-разделителя
        splitPane.setId(splitPaneID);

        // Добавление панели-разделителя к интерфейсу программы
        tabManagerView.addSplitPane(parentPane, splitPane, splitType);

        // Добавление панели-разделителя в список панелей-разделителей
        splitPaneList.add(splitPane);

        // Возврат пары значений "Идентификатор-Панель"
        return new Pair<>(splitPaneID, splitPane);
    }

    /** ---------------------------------------------------- *
     * Поиск панели разделителя                              *
     * @param splitPaneID - идентификатор панели-разделителя *
     * ----------------------------------------------------- */
    SplitPane findSplitPane(String splitPaneID) {
        List<SplitPane> findedParentList = splitPaneList.stream()
                .filter(pane -> pane.getId().equals(splitPaneID))
                .collect(Collectors.toList());

        return findedParentList.isEmpty() ? null : findedParentList.get(0);
    }

    /**
     * Запрос на создание панели вкладок
     * (панель вкладок добавляется на панель, найденную по значению переданного идентификатора)
     *
     * На вход:
     *      @param parentPaneID - идентификатор родительской панели
     *      @param config - конфигурация создаваемой панели вкладок
     *
     * На выход:
     *      @return tabPaneID - идентификатор создаваемой панели вкладок
     *              tabPane - создаваемая панель вкладок
     */
    public Pair<String, TabPane> requestCreateTabPane(String parentPaneID, JsonObject config) {
        if (parentPaneID.equals(""))
            return requestCreateTabPane(((Parent) null), config);
        else if (parentPaneID.equals("anchorPane"))
            return requestCreateTabPane(anchorPane, config);
        else
            return requestCreateTabPane(findSplitPane(parentPaneID), config);
    }

    /**
     * Запрос на создание панели вкладок
     * (панель вкладок добавляется на переданную в параметрах панель)
     *
     * На вход:
     *      @param parentPane - родительская панель
     *      @param config - конфигурация создаваемой панели вкладок
     *
     * На выход:
     *      @return tabPaneID - идентификатор создаваемой панели вкладок
     *              tabPane - создаваемая панель вкладок
     */
    public Pair<String, TabPane> requestCreateTabPane(Parent parentPane, JsonObject config) {

        LOGGER.info("Создание модели панели-разделителя");
        String tabPaneID = tabManagerModel.createTabPaneModel();

        LOGGER.info("Создание контроллера панелей вкладок из загрузчика FXML");
        TabPaneController tabPaneController = new TabPaneController();

        LOGGER.info("Добавление контроллера панелей вкладок в список контроллеров");
        tabPaneControllerList.add(tabPaneController);

        LOGGER.info("Установка фокуса (если фокус не установлен ни на одну из панелей)");
        if (!mainController.isAnyPaneFocused())
            mainController.requestChangeFocusedTabPaneController(tabPaneController);

        LOGGER.info("Установка идентификатора панели вкладок");
        TabPane tabPane = tabPaneController.getTabPane();
        tabPane.setId(tabPaneID);

        LOGGER.info("Добавление панели вкладок на панель-разделитель/основную панель");
        tabManagerView.addTabPaneToParent(parentPane, tabPane);

        if (config.size() != 0) {
            LOGGER.info("Запрос на загрузку конфигурации панели вкладок");
            tabPaneController.requestLoadConfig(config);
        }

        LOGGER.info("Добавление поддержки Drag-And-Drop");
        addDragAndDropSupport(tabPane);

        LOGGER.info("Добавление наблюдателя за нажатием на панель");
        tabPane.pressedProperty().addListener((observable, oldValue, newValue) -> {
            // Произошло нажатие на панель
            if (newValue) mainController.requestChangeFocusedTabPaneController(tabPaneController);
        });

        LOGGER.info("Возврат пары значений Идентификатор-Панель");
        return new Pair<>(tabPaneID, tabPane);
    }


//    /**
//     * Запроос на открытие страницы
//     * @param pageType - тип страницы
//     */
//    public void requestOpenPage(String pageType) {
//        focusedTabPaneController.requestOpenPage(pageType);
//    }

























    @FXML
    public void onRegionDragDropped(DragEvent dragEvent) {
        System.out.println("onRegionDragDropped");

        // Получение региона, c которым произошло пересечение вкладки
        Region region = ((Region) dragEvent.getTarget());

        if (List.of("top", "left", "right", "bottom").contains(region.getId())) {
            TabPane tabPane = requestCreateTabPane("", new JsonObject()).getValue();
            currentDraggingTab.getTabPane().getTabs().remove(currentDraggingTab);
            tabPane.getTabs().add(currentDraggingTab);

            SplitPane splitPane;
            switch (region.getId()) {
                case "top" -> {
                    splitPane = requestCreateSplitPane("", TabPaneModel.VERTICAL_SPLIT_PANE).getValue();
                    splitPane.getItems().add(tabPane);
                    splitPane.getItems().addAll(anchorPane.getChildren());
                }
                case "left" -> {
                    splitPane = requestCreateSplitPane("", TabPaneModel.HORIZONTAL_SPLIT_PANE).getValue();
                    splitPane.getItems().add(tabPane);
                    splitPane.getItems().addAll(anchorPane.getChildren());
                }
                case "right" -> {
                    splitPane = requestCreateSplitPane("", TabPaneModel.HORIZONTAL_SPLIT_PANE).getValue();
                    splitPane.getItems().addAll(anchorPane.getChildren());
                    splitPane.getItems().add(tabPane);
                }
                default -> {
                    splitPane = requestCreateSplitPane("", TabPaneModel.VERTICAL_SPLIT_PANE).getValue();
                    splitPane.getItems().addAll(anchorPane.getChildren());
                    splitPane.getItems().add(tabPane);
                }
            }
            anchorPane.getChildren().add(splitPane);
            AnchorPane.setTopAnchor(splitPane, 0.0);
            AnchorPane.setBottomAnchor(splitPane, 0.0);
            AnchorPane.setRightAnchor(splitPane, 0.0);
            AnchorPane.setLeftAnchor(splitPane, 0.0);
        }

    }

    @FXML
    public void onRegionDragEntered(DragEvent dragEvent) {
        System.out.println("onRegionDragEntered");
    }

    @FXML
    public void onRegionDragExited(DragEvent dragEvent) {
        ((Region) dragEvent.getTarget()).setStyle("-fx-background-color: ghostwhite");
        System.out.println("onRegionDragExited");
    }

    @FXML
    public void onRegionDragOver(DragEvent dragEvent) {
        // Передвигаемый объект является вкладкой
        if (draggingID.equals(dragEvent.getDragboard().getString()) &&
                currentDraggingTab != null) {

            // Разрешить перемещение вкладки
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }

        ((Region) dragEvent.getTarget()).setStyle("-fx-background-color: green");
    }




    void onTabDragging(Event event) {
        Tab tab =  (Tab) event.getSource();
        tab.setGraphic(new Rectangle(16, 16, Color.RED));

    }

    private Tab currentDraggingTab ;

    // Генерация ID перемещаемой вкладки
    private static final AtomicLong idGenerator = new AtomicLong();
    private final String draggingID = "DraggingTabPaneSupport-"+idGenerator.incrementAndGet() ;

    /**
     * Метод добавления поддержки Drag-and-Drop ко вкладкам
     *
     * 1. Добавляется поддержка Drag-and-Drop к уже существующим вкладкам
     * 2. Добавляется Listener в виде lambda-функции
     * @see ListChangeListener.Change - слушатель изменений списка из вкладок
     * 1) При добавлении новой вкладки добавляет ей поддержку Drag-And-Drop
     * 2) При удалении вкладки - убирает ей поддержку Drag-And-Drop
     * @param tabPane  - панель вкладок
     */
    public void addDragAndDropSupport(TabPane tabPane) {
        // Add listeners for "tabPane" for adding/removing DragHandlers
        tabPane.getTabs().forEach(this::addDragHandlers);
        tabPane.getTabs().addListener((ListChangeListener.Change<? extends Tab> c) -> {
            while (c.next()) {
                // Вкладка была добавлена
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(this::addDragHandlers);
                }

                // Вкладка была удалена
                if (c.wasRemoved()) {
                    c.getRemoved().forEach(this::removeDragHandlers);
                }
            }
        });

        // if we drag onto a tab pane (but not onto the tab graphic), add the tab to the end of the list of tabs:
        /*tabPane.setOnDragOver(e -> {
            if (draggingID.equals(e.getDragboard().getString()) &&
                    currentDraggingTab != null &&
                    currentDraggingTab.getTabPane() != tabPane) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });
        tabPane.setOnDragDropped(e -> {
            if (draggingID.equals(e.getDragboard().getString()) &&
                    currentDraggingTab != null &&
                    currentDraggingTab.getTabPane() != tabPane) {
                currentDraggingTab.getTabPane().getTabs().remove(currentDraggingTab);
                tabPane.getTabs().add(currentDraggingTab);
                currentDraggingTab.getTabPane().getSelectionModel().select(currentDraggingTab);
            }
        });*/
    }

    /**
     * Adding DragHandlers for Tabs
     * @param tab - tab
     */
    private void addDragHandlers(Tab tab) {


        // move text to label graphic:
        // Текст и картинка рядом с ним объединяются в единый Label,
        // т.е. в заголовок вкладки, к которому затем привязывается Drag-And-Drop
        if (tab.getText() != null && !tab.getText().isEmpty()) {
            Label label = new Label(tab.getText());
            System.out.println("set graphic");
            tab.setText(null);
            tab.setGraphic(label);
        }

        Node graphic = tab.getGraphic();
        System.out.println(graphic);

        // Обработка обнаружения переноса вкладки
        graphic.setOnDragDetected(e -> {

            // Отладочный вывод позиции заголовка вкладки
            System.out.println(e.getSceneX() + "," + e.getSceneY());
            System.out.println(e.getX() + "," + e.getY());

            Dragboard dragboard = graphic.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            // dragboard must have some content, but we need it to be a Tab, which isn't supported
            // So we store it in a local variable and just put arbitrary content in the dragbaord:
            // (в качестве контента использует уникальный идентификатор вкладки)
            content.putString(draggingID);
            dragboard.setContent(content);

            dragboard.setDragView(tab.getTabPane().snapshot(null, null));
            //dragboard.setDragView(graphic.snapshot(null, null));
            currentDraggingTab = tab;

            //dragboard.getTransferModes().
        });

        graphic.setOnDragOver(e -> {
            System.out.println("draggingID:" + draggingID);
            System.out.println("eventID: " + e.getDragboard().getString());
            if (draggingID.equals(e.getDragboard().getString()) &&
                    currentDraggingTab != null &&
                    currentDraggingTab.getGraphic() != graphic) {
                System.out.println("Here");
                //e.acceptTransferModes(TransferMode.MOVE);
            }
        });
        graphic.setOnDragDropped(e -> {
            if (draggingID.equals(e.getDragboard().getString()) &&
                    currentDraggingTab != null &&
                    currentDraggingTab.getGraphic() != graphic) {

                int index = tab.getTabPane().getTabs().indexOf(tab);
                currentDraggingTab.getTabPane().getTabs().remove(currentDraggingTab);
                tab.getTabPane().getTabs().add(index, currentDraggingTab);
                currentDraggingTab.getTabPane().getSelectionModel().select(currentDraggingTab);
            }
            else if (draggingID.equals(e.getDragboard().getString()) &&
                    currentDraggingTab != null
            ) {
                System.out.println(e.getSceneX() + "," + e.getSceneY());
                System.out.println(e.getX() + "," + e.getY());
            }


        });
        graphic.setOnDragDone(e -> {
            currentDraggingTab = null;
            // tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        });
    }

    private void removeDragHandlers(Tab tab) {
        tab.getGraphic().setOnDragDetected(null);
        tab.getGraphic().setOnDragOver(null);
        tab.getGraphic().setOnDragDropped(null);
        tab.getGraphic().setOnDragDone(null);
    }





    /*
    TODO: Не удалять SplitPane при удалении TabPane, добавить кнопки
    TODO: Свои панели на главной странице для разных разделов
    TODO: UTF-8 в другой консоли
    TODO: Система логирования
    TODO: Добавить BorderPane, как обёртку для SplitPane
     */
}
