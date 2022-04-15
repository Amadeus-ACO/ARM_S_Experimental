package client.mainWindow.pages.labPages.labListPage;

import client.mainWindow.pages.labPages.labListPage.labDescription.LabDescriptionController;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class LabListPageView {

    @FXML
    TreeView<String> labTree;

    @FXML
    LabDescriptionController labDescriptionController;

    protected void addThemeToTreeView(String themeName) {
        TreeItem<String> item = new TreeItem<>(themeName);
        labTree.getRoot().getChildren().add(item);
    }

    protected void addLabToTreeView(String labName, String themeName) {
        FilteredList<TreeItem<String>> resultList = labTree.getRoot().getChildren().filtered(
            stringTreeItem -> stringTreeItem.getValue().equals(themeName)
        );
        if (!resultList.isEmpty()) {
            TreeItem<String> labItem = new TreeItem<>(labName);
            resultList.get(0).getChildren().add(labItem);
        }
    }

    protected void initLabTreeView() {
        labTree.setRoot(new TreeItem<>("Темы лабораторных работ"));
    }
}
