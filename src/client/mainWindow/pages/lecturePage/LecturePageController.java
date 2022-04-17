package client.mainWindow.pages.lecturePage;

import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

import java.util.Objects;

public class LecturePageController {

    @FXML
    private AnchorPane lecturePageRoot;

    @FXML
    private TreeView<String> lecturePageTreeView;

    @FXML
    private WebView lecturePageWebView;

    @FXML
    public void initialize(){

        lecturePageTreeView.setCellFactory(param -> {

            TreeCell<String> cell = new TreeCell<>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item);
                    }
                }
            };

            cell.setOnMouseClicked(event -> {
                try {
                    lecturePageWebView.getEngine().
                            load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                                    "lect/Lect" + cell.getTreeItem().getValue().split(" ")[1] + ".html")).toString());
                }catch (Exception e){

                }
            });

            return cell;

        });

        TreeItem<String> treeRoot = new TreeItem<>("Лекции:");

        lecturePageTreeView.setRoot(treeRoot);

        for (int i = 1; i <= 16; i++){
            treeRoot.getChildren().add(new TreeItem<>("Лекция " + i));
        }

    }

    public Region getRoot() {
        return lecturePageRoot;
    }

}
