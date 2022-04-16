package client.mainWindow.sectionMenu;

import client.mainWindow.MainController;
import client.mainWindow.MainModel;
import client.mainWindow.pages.Pages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SectionMenuController extends SectionMenuView implements Initializable {

    @FXML
    private Button labListButton;

    private SectionMenuModel sectionMenuModel = new SectionMenuModel();
    private SectionMenuView sectionMenuView = new SectionMenuView();
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onTrainButtonClick() {
        MainController.getInstance().requestOpenPage(Pages.TRAINING_PAGE);
    }

    @FXML
    void onLabListButtonClick() {
        mainController.requestOpenPage(Pages.LAB_LIST_PAGE);
    }

}
