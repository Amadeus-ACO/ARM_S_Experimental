package client.mainWindow.tab;

import AmadeyLogicGame.AmadeyLogicGame;
import LogisimFX.Startup;
import LogisimFX.newgui.FrameManager;
import client.Config;
import client.mainWindow.pages.Pages;
import client.mainWindow.pages.labPages.labListPage.LabListPageController;
import client.mainWindow.pages.labPages.labPage.LabPageController;
import client.mainWindow.pages.mainPage.MainPageController;
import client.mainWindow.pages.trainingPage.TrainerPageController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import entity.Entity;
import entity.EntityMap;
import entity.Section;
import entity.work.GivenTask;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TabController {

    private Tab tab;
    private TabModel tabModel = new TabModel(this);
    private TabView tabView = new TabView(this);

    public TabController() {
        tab = new Tab();
        tabView.initTab(tab);
        tab.setOnCloseRequest(this::onCloseRequest);
    }

    public Tab getTab() {
        return tab;
    }

    public void requestLoadConfig(JsonObject config) {
        tabModel.loadConfig(config);
    }

    public void requestOpenPage(JsonObject config) {
        String type = config.get("type").getAsString();
        requestOpenPage(type);
    }

    public void requestOpenPage(String type, Object... params) {
        // Какая-то работа с контроллером страницы....
        switch (type) {

            case Pages.MAIN_PAGE -> {
                MainPageController mainPageController = new MainPageController();
                tab.setText(type);
                tab.setContent(mainPageController.getRoot());
            }

            case Pages.AOVT_PAGE -> {
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aovt_start.fxml")));
                    tab.setText(type);
                    ScrollPane scrollPane = new ScrollPane(root);
                    scrollPane.setFitToWidth(true);
                    scrollPane.setFitToHeight(true);
                    ((AnchorPane) root).setMaxWidth(Region.USE_COMPUTED_SIZE);
                    ((AnchorPane) root).setMaxHeight(Region.USE_COMPUTED_SIZE);
                    tab.setContent(scrollPane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            case Pages.LAB_LIST_PAGE -> {
                LabListPageController labListController = new LabListPageController();
                labListController.update((List<GivenTask>) params[0], (Section) params[1]);
                tab.setText(type);
                tab.setContent(labListController.getRoot());
                //tab.setContent(labListController.getRoot());
            }

            case Pages.LAB_PAGE -> {
                //Config.load("lab")
                LabPageController labPageController = new LabPageController();
                tab.setText(type);
                tab.setContent(labPageController.getRoot());
            }

            case Pages.TRAINING_PAGE -> {
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main_window/pages/trainers_list_page/trainersPage.fxml")));
                    tab.setText(type);
                    tab.setContent(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            case Pages.ALG_PAGE -> {
                  tab.setText(type);
                  tab.setContent(AmadeyLogicGame.load((String[]) params).getRoot());
                  tab.setOnCloseRequest(event -> AmadeyLogicGame.terminateApp());
            }

            case Pages.LOGISIM_PAGE -> {
                //Platform.runLater(() -> {
                tab.setText(type);
                Startup startup = Startup.parseArgs((String[]) params);
                startup.setOnSucceeded(event -> tab.setContent(FrameManager.getScene().getRoot()));

                startup.run();
                //tab.setContent(FrameManager.scene.getRoot());
                //FrameManager.stage.close();
                //});
            }

            case Pages.LECTURE_PAGE -> {

                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main_window/pages/lecture_page/lecturePage.fxml")));
                    tab.setText(type);
                    tab.setContent(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                LecturePageController lecturePageController = new LecturePageController();
                tab.setText(type);
                tab.setContent(lecturePageController.getRoot());

                 */
            }
            default -> {
                System.out.println("Неправильная страница");
            }
        }

        // tab.getContent().focusedProperty().addListener(this::onFocusChanged);
        // ((Parent) tab.getContent()).getChildrenUnmodifiable().
        // ((Pane) ((ScrollPane) tab.getContent()).getContent()).getChildren().forEach(child -> child.focusedProperty().addListener(this::onFocusChanged));
    }

    private void onCloseRequest(Event event) {
        tab.getTabPane().fireEvent(event);
    }

    private void onFocusChanged(Observable observable, boolean oldValue, boolean newValue) {
        System.out.println("\nTAB_Focus");
        System.out.println(observable);
        System.out.println(oldValue);
        System.out.println(newValue+"\n");
        tab.getTabPane().setStyle(newValue ? "-fx-border-color: fuchsia; -fx-border-style: solid; -fx-border-width: 1;" : "");
    }
}
