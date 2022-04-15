package test;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class FXSplitTabs extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SplitTabs");
        stage.setWidth(700);
        stage.setHeight(500);

        //Setup Center and Right
        TabPaneWrapper wrapper = new TabPaneWrapper(Orientation.HORIZONTAL, .9);
        TabPane centerPane = new TabPane();
        centerPane.getTabs().addAll(generateTab("Tab 1"),generateTab("Tab 2"), generateTab("Tab 3"), generateTab("Tab N"));

        TabPane rightPane = new TabPane();
        rightPane.getTabs().addAll(generateTab("Tab 1"),generateTab("Tab 2"), generateTab("Tab 3"), generateTab("Tab N"));
        SplitPane.setResizableWithParent(rightPane, false);
        wrapper.addNodes(centerPane, rightPane);

        //Add bottom
        TabPane bottomPane = new TabPane();
        bottomPane.getTabs().addAll(generateTab("Tab 1"),generateTab("Tab 2"), generateTab("Tab 3"), generateTab("Tab N"));
        TabPaneWrapper wrapperBottom = new TabPaneWrapper(Orientation.VERTICAL, .7);
        wrapperBottom.addNodes(wrapper.getNode(), bottomPane);

        //Add left
        TabPane leftPane = new TabPane();
        leftPane.getTabs().addAll(generateTab("Tab 1"),generateTab("Tab 2"), generateTab("Tab 3"), generateTab("Tab N"));
        TabPaneWrapper wrapperleft = new TabPaneWrapper(Orientation.HORIZONTAL, .1);
        wrapperleft.addNodes(leftPane, wrapperBottom.getNode());

        Scene myScene = new Scene(wrapperleft.getNode());
        stage.setScene(myScene);
        stage.sizeToScene();
        stage.show();
    }

    public Tab generateTab(String name){
        Tab result = new Tab(name);
        BorderPane content = new BorderPane();
        TextArea text = new TextArea();
        content.setCenter(text);
        result.setContent(content);
        return result;
    }

    public static void main(String[] args){
        FXSplitTabs.launch(args);
    }

    public static class TabPaneWrapper{
        SplitPane split;

        public TabPaneWrapper(Orientation o, double splitLocation){
            split = new SplitPane();

            //Change the CSS (uncomment if using an external css)
            //split.getStylesheets().add("test.css");

            split.setOrientation(o);
            split.setDividerPosition(0, splitLocation);
        }

        public void addNodes(final Node node1, final Node node2){
            //Add to the split pane
            split.getItems().addAll(node1, node2);
        }

        public Parent getNode(){
            return split;
        }
    }

}