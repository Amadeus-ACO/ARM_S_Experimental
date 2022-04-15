package client.mainWindow.tabs;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TabsZoneController extends TabsZoneView implements Initializable {
    private TabsZoneModel tabsZoneModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabsZoneModel = new TabsZoneModel();
        createTabPane();

        tabsZoneModel.getTabPaneModelList().get(0).createTabModel("Main");
        //tabsZoneModel.createTabModel(TabsZoneModel.MAIN_PAGE);

        //tabPane.getTabs().get(0).setGraphic(new Rectangle(16, 16, Color.RED));
        //addDragAndDropSupport(tabPane);
    }
}
