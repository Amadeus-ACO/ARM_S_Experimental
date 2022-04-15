package client.mainWindow.pages.mainPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

import static tools.Tools.getImageSVG;

public class MainPageController extends View implements Initializable {

    private MainPageModel mainPageModel = new MainPageModel();
    private Parent root;

    public MainPageController() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main_window/pages/main_page/mainPage.fxml"));
        fxmlLoader.setRoot(gridPane);
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onAddPanelButtonReleased(MouseEvent mouseEvent) throws UnsupportedEncodingException {
        onAddPanelButtonEntered(mouseEvent);
        gridPane.add(mainPageModel.createPanel(), gridPane.getColumnCount()-1, 0);
        GridPane.setConstraints(addPanelButton, gridPane.getColumnCount(), 0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addPanelButton.setImage(getImageSVG("main_window/pages/main_page/addButton.svg"));
        try {
            gridPane.add(mainPageModel.createPanel(), gridPane.getColumnCount()-1, 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        GridPane.setConstraints(addPanelButton, gridPane.getColumnCount(), 0);
    }

    public Parent getRoot() {
        return root;
    }
}
