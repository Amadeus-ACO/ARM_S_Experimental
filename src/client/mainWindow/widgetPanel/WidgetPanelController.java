package client.mainWindow.widgetPanel;

import entity.user.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WidgetPanelController extends WidgetPanelView implements Initializable {

    @FXML
    private GridPane widgetGridPane;

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label studentGroupNameLabel;

    private WidgetPanelModel widgetPanelModel = new WidgetPanelModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatePickerSkin skin = new DatePickerSkin(new DatePicker());
        Node calendarControl = skin.getPopupContent();
        widgetGridPane.add(calendarControl,0,6);
        widgetGridPane.autosize();
    }

    public void requestUpdate(Student student) {
        // Model
        widgetPanelModel.update(student.getName(),
                                student.getStudentGroup().getName()
        );

        // Controller
        studentNameLabel.setText(widgetPanelModel.getStudentName());
        studentGroupNameLabel.setText(widgetPanelModel.getStudentGroupName());

        // View
    }
}
