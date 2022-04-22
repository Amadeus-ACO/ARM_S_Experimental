package client.mainWindow.pages.mainPage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.UnsupportedEncodingException;

public class MainPageModel {

    public VBox createPanel() throws UnsupportedEncodingException {
        VBox panelVBox = new VBox();
        panelVBox.getStyleClass().add("panelVBox");
        panelVBox.setAlignment(Pos.TOP_CENTER);
        panelVBox.setFillWidth(true);
        GridPane.setHalignment(panelVBox, HPos.CENTER);
        GridPane.setValignment(panelVBox, VPos.CENTER);
        GridPane.setMargin(panelVBox, new Insets(20, 20, 20, 20));

        ChoiceBox<String> titleChoiceBox = new ChoiceBox<>();
        titleChoiceBox.getStyleClass().add("titleChoiceBox");
        titleChoiceBox.setItems(getTitlePanelList());
        titleChoiceBox.getSelectionModel().select(0);
        VBox.setMargin(titleChoiceBox, new Insets(10,10,10,10));

        Separator separator = new Separator();
        separator.getStyleClass().add("separator");
        separator.setOrientation(Orientation.HORIZONTAL);
        separator.setHalignment(HPos.CENTER);
        separator.setValignment(VPos.CENTER);

        Text panelText = new Text("Text");
        VBox.setMargin(panelText, new Insets(10,10,10,10));
        panelText.setFont(new Font("Consolas", 16));
        panelText.setText("Главная страница");

        panelVBox.getChildren().addAll(titleChoiceBox, separator, panelText);
        return panelVBox;
    }

    private ObservableList<String> getTitlePanelList() throws UnsupportedEncodingException {
        return FXCollections.observableArrayList(
                "Последние события",
                "Custom Page"
        );
    }
}
