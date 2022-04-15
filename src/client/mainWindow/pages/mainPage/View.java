package client.mainWindow.pages.mainPage;

import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class View {
    @FXML
    private Text lastEventsText;

    @FXML
    private Text customPanelText;

    @FXML
    protected ImageView addPanelButton;

    @FXML
    protected GridPane gridPane;

    @FXML
    void onAddPanelButtonEntered(MouseEvent event) {
        System.out.println("addPanelButton enter");
        ImageView imageView = ((ImageView) event.getSource());
        imageView.setEffect(new ColorAdjust(-0.69,0.47,-0.13, 0.15));
    }

    @FXML
    void onAddPanelButtonExited(MouseEvent event) {
        System.out.println("addPanelButton exit");
        ImageView imageView = ((ImageView) event.getSource());
        imageView.setEffect(new ColorAdjust());
    }

    @FXML
    void onAddPanelButtonPressed(MouseEvent event) {
        System.out.println("addPanelButton pressed");
        ImageView imageView = ((ImageView) event.getSource());
        imageView.setEffect(new ColorAdjust(0.7,0.47,-0.13, 0.15));
    }
}
