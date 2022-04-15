package client.mainWindow.pages.labPages.labListPage;

import client.mainWindow.pages.labPages.labListPage.labDescription.LabDescriptionController;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LabListPageController extends LabListPageView implements Initializable {

    private final static String FXML_PATH = "main_window/pages/lab_list_page/labListPage.fxml";

    @FXML
    TreeView<String> labTree;

    @FXML
    LabDescriptionController labDescriptionController;

    private SplitPane root;
    public SplitPane getRoot() {
        return root;
    }

    private LabListPageModel labListPageModel = new LabListPageModel(this);
    private LabListPageView labListPageView = this;

    public LabListPageController(JsonObject config) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(FXML_PATH));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        labListPageView.initLabTreeView();
        labListPageModel.loadConfig(config);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void requestLoadConfig(JsonObject config) {
        labListPageModel.loadConfig(config);
    }

    public void requestAddThemeToTreeView(String themeName) {
        labListPageView.addThemeToTreeView(themeName);
    }


    public void requestAddLabToTreeView(String themeName, String labName) {
        labListPageView.addLabToTreeView(labName, themeName);
    }
}

/* TODO:
      Фильтрация лаб:
      - по темам
      - по статусу выполнения

*
* */