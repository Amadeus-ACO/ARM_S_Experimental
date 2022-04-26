package client.mainWindow.widgetPanel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WidgetPanelController implements Initializable {

    @FXML
    private GridPane widgetGridPane;

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
        DatePickerSkin skin = new DatePickerSkin(new DatePicker());
        Node calendarControl = skin.getPopupContent();
        widgetGridPane.add(calendarControl,0,6);
        widgetGridPane.autosize();
    }

}
