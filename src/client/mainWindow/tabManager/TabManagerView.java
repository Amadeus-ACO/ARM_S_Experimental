package client.mainWindow.tabManager;


import client.mainWindow.tabPane.TabPaneModel;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class TabManagerView {

    @FXML
    BorderPane borderPane;

    @FXML
    AnchorPane anchorPane;

    protected ArrayList<SplitPane> splitPaneList = new ArrayList<>();

    void setBounds(
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

        //gridPane.setPrefWidth(width);
        //gridPane.setPrefHeight(height);
    }

    void updateSeparators() {
        /*for (int i = 0; i < gridPane.getRowCount(); i++) {
            fo
        }*/
    }

    protected void addSplitPane(Parent parentPane, SplitPane splitPane, String paneType) {
        if (paneType.equals(TabPaneModel.HORIZONTAL_SPLIT_PANE))
            splitPane.setOrientation(Orientation.HORIZONTAL);
        else if (paneType.equals(TabPaneModel.VERTICAL_SPLIT_PANE))
            splitPane.setOrientation(Orientation.VERTICAL);
        addPane(parentPane, splitPane);
    }

    protected void addTabPaneToParent(Parent parentPane, TabPane tabPane) {
        addPane(parentPane, tabPane);
    }


    private void addPane(Parent parentPane, Parent childPane) {
        if (parentPane != null) {
            if (parentPane instanceof AnchorPane) {
                ((AnchorPane) parentPane).getChildren().add(childPane);
                AnchorPane.setTopAnchor(childPane, 0.0);
                AnchorPane.setBottomAnchor(childPane, 0.0);
                AnchorPane.setRightAnchor(childPane, 0.0);
                AnchorPane.setLeftAnchor(childPane, 0.0);
            }
            else if (parentPane instanceof SplitPane) {
                ((SplitPane) parentPane).getItems().add(childPane);
            }
        }
    }
}
