package client.mainWindow.widgetPanel;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class WidgetPanelController implements Initializable {

    private WidgetPanelView widgetPanelView;
    private WidgetPanelModel widgetPanelModel;

    public void setWidgetPanelView(WidgetPanelView widgetPanelView) {
        this.widgetPanelView = widgetPanelView;
    }
    public void setWidgetPanelModel(WidgetPanelModel widgetPanelModel) {
        this.widgetPanelModel = widgetPanelModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
