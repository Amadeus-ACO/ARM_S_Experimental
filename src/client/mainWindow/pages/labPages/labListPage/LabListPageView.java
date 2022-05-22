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

    }

    protected void addLabToTreeView(String labName, String themeName) {

    }

    protected void initLabTreeView() {

    }
}
