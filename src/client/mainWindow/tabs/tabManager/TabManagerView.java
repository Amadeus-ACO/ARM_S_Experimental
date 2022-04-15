package client.mainWindow.tabManager;


import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TabManagerView {

    @FXML
    BorderPane borderPane;

    @FXML
    AnchorPane anchorPane;

    @FXML
    GridPane gridPane;

    void configGridPane(
                        //int rows,
                        //int columns,
                        double width,
                        double height) {
//        anchorPane.getChildren().remove(gridPane);
//
//        SplitPane splitPane;
//        for (int i = 0; i < rows; i++) {
//            splitPane = new SplitPane();
//            splitPane.setOrientation(Orientation.HORIZONTAL);
//
//            for (int j = 0; j < columns; j++)
//            anchorPane.getChildren().add(new SplitPane();
//        }

        gridPane.setPrefWidth(width);
        gridPane.setPrefHeight(height);
    }

    void updateSeparators() {
        /*for (int i = 0; i < gridPane.getRowCount(); i++) {
            fo
        }*/
    }
}
