package client.mainWindow.sectionMenu;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class SectionMenuView {

    @FXML
    private Button labButton;

    @FXML
    private Button trainButton;

    @FXML
    private Button statisticsButton;

    @FXML
    private Button lectureButton;

    @FXML
    void onSectionEntered(MouseEvent event) {
        System.out.println("enter");
        ObservableList<String> cssList = ((Button) event.getSource()).getStyleClass();
        cssList.remove(cssList.size()-1); cssList.add("sectionButton_entered");
    }

    @FXML
    void onSectionExited(MouseEvent event) {
        System.out.println("exit");
        ObservableList<String> cssList = ((Button) event.getSource()).getStyleClass();
        cssList.remove(cssList.size()-1); cssList.add("sectionButton_default");
    }

    @FXML
    void onSectionPressed(MouseEvent event) {
        System.out.println("pressed");
        ObservableList<String> cssList = ((Button) event.getSource()).getStyleClass();
        cssList.remove(cssList.size()-1); cssList.add("sectionButton_pressed");
    }

    @FXML
    void onSectionReleased(MouseEvent event) {
        System.out.println("released");
        ObservableList<String> cssList = ((Button) event.getSource()).getStyleClass();
        cssList.remove(cssList.size()-1); cssList.add("sectionButton_entered");
    }
}
