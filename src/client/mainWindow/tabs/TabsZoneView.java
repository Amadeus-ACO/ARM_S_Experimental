package client.mainWindow.tabs;

import client.mainWindow.pages.MainPage.Controller;
import client.mainWindow.pages.MainPage.Model;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class TabsZoneView {

    // Список существующих панелей с вкладками
    ArrayList<TabPane> tabPaneList;

    @FXML
    AnchorPane anchorPane;

    @FXML
    BorderPane borderPane;

    /**
     * Добавление новой панели вкладок
     * @return TabPane - созданная пустая панель
     */
    protected TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPaneList.add(tabPane);
        return tabPane;
    }

    /**
     * Создание новой вкладки
     * @param tapType
     * @return Tab - новая вкладка
     */
    protected Tab createNewTab(String tapType) {
        System.out.println("create new page " + tapType);


        switch (tapType) {
            // Главная страница
            case "Main" -> {

            }
            case "AOVT" -> {
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aovt_start.fxml")));
                    //pageTab.setText("AOVT");
                    ((AnchorPane) root).setMaxWidth(Region.USE_COMPUTED_SIZE);
                    ((AnchorPane) root).setMaxHeight(Region.USE_COMPUTED_SIZE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //pageTab.setContent(root);
            }
        }
        // tabPane.getTabs().add(pageTab);
        return null;
    }

    /**
     * Создание главной страницы
     * @return Tab - вкладка с информацией по главной странице
     */
    private Tab createMainTab() {
        Tab pageTab = new Tab();
        pageTab.setText("Главная страница");
        Controller mainPageController = new Controller(new Model());
        System.out.println(mainPageController.getRoot());
        pageTab.setContent(mainPageController.getRoot());
        return pageTab;
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
        tabPane.setOnDragOver(e -> {
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
        });
    }

    /**
     * Adding DragHandlers for Tabs
     * @param tab - tab
     */
    private void addDragHandlers(Tab tab) {

        String text = tab.getText();

        // move text to label graphic:
        // Текст и картинка рядом с ним объединяются в единый Label,
        // т.е. в заголовок вкладки, к которому затем привязывается Drag-And-Drop
        if (tab.getText() != null && ! tab.getText().isEmpty()) {
            Label label = new Label(tab.getText(), tab.getGraphic());
            tab.setText(null);
            tab.setGraphic(label);
        }

        Node graphic = tab.getGraphic();

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

            //dragboard.setDragView(tabPane.snapshot(null, null));
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
            //tab.setGraphic(null);
            //tab.setText(text);
        });
    }

    private void removeDragHandlers(Tab tab) {
        tab.getGraphic().setOnDragDetected(null);
        tab.getGraphic().setOnDragOver(null);
        tab.getGraphic().setOnDragDropped(null);
        tab.getGraphic().setOnDragDone(null);
    }


}
