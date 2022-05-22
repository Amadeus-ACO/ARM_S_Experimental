package client.mainWindow.pages.labPages.labListPage;

import client.mainWindow.pages.labPages.labListPage.labDescription.LabDescriptionController;
import entity.Section;
import entity.work.GivenTask;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import main.Web;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LabListPageController extends LabListPageView implements Initializable {

    private final static String FXML_PATH = "main_window/pages/lab_list_page/labListPage.fxml";
    private final static String ROOT_NAME = "Работы";

    @FXML
    TreeView<String> labTree;

    @FXML
    LabDescriptionController labDescriptionController;

    private SplitPane root;
    public SplitPane getRoot() {
        return root;
    }

    private final LabListPageModel labListPageModel = new LabListPageModel(this);

    public LabListPageController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(FXML_PATH));
        fxmlLoader.setController(this);
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //labTree.setRoot(new TreeItem<>(ROOT_NAME));

        labTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onTreeLabClick(newValue));
        // initLabTreeView();
        // labListPageModel.loadConfig(config);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void update(List<GivenTask> givenTaskList, Section rootSection) {
        // Model
        labListPageModel.update(givenTaskList);

        // Controller
        addSectionInTree(rootSection, null);

        for (GivenTask givenTask: givenTaskList)
            requestAddLabToTree(givenTask.getTaskVariant().getTask().getSection().getName(),
                givenTask.getTaskVariant().getTask().getName());
    }

    private void addSectionInTree(Section section, TreeItem<String> parentTreeItem) {
        TreeItem<String> currentTreeItem = new TreeItem<>(section.getName());
        if (parentTreeItem == null || section.getParentSection() == null)
            labTree.setRoot(currentTreeItem);
        else
            parentTreeItem.getChildren().add(currentTreeItem);
        for(Section childSection : section.getChildren()) {
            addSectionInTree(childSection, currentTreeItem);
        }
    }

    private TreeItem<String> findTreeItemBySectionName(String sectionName) {
        if (sectionName.equals(labTree.getRoot().getValue()))
            return labTree.getRoot();
        else return findTreeItemBySectionName(sectionName, labTree.getRoot());
    }

    private TreeItem<String> findTreeItemBySectionName(String sectionName, TreeItem<String> parentTreeItem) {
        if (parentTreeItem.getChildren().isEmpty())
            return null;
        FilteredList<TreeItem<String>> findList = parentTreeItem.getChildren().filtered(treeItem ->
            treeItem.getValue().equals(sectionName));
        if (!findList.isEmpty())
            return findList.get(0);
        else
            for (TreeItem<String> childItem: parentTreeItem.getChildren()) {
                TreeItem<String> findItem = findTreeItemBySectionName(sectionName, childItem);
                if (findItem != null)
                    return findItem;
            }
        return null;
    }

    public void requestAddLabToTree(String sectionName, String labName) {
        if (sectionName.equals(labTree.getRoot().getValue()))
            labTree.getRoot().getChildren().add(new TreeItem<>(labName));
        else {
            TreeItem<String> sectionParent = findTreeItemBySectionName(sectionName);
            sectionParent.getChildren().add(new TreeItem<>(labName));
        }
    }

    @FXML
    public void onTreeLabClick(TreeItem<String> treeItem) {
        if (treeItem.getChildren().isEmpty()) {
            GivenTask shortGivenTask = labListPageModel.getGivenTaskList().stream().filter(givenTask ->
                    givenTask.getTaskVariant().getTask().getName().equals(treeItem.getValue())).findFirst().get();
            if (//shortGivenTask.getCompleteStatus() == GivenTask.COMPLETE_STATUS.COMPLETED ||
                shortGivenTask.getCompleteStatus() == GivenTask.COMPLETE_STATUS.WAITING_FOR_CHECK ||
                shortGivenTask.getCompleteStatus() == GivenTask.COMPLETE_STATUS.WAITING_FOR_FINAL_TEST_CHECK
            ) {
                labDescriptionController.update(shortGivenTask);
            }
            else {
                GivenTask fullGivenTask = Web.getGivenTaskDescription(shortGivenTask);
                labDescriptionController.update(fullGivenTask);
            }
        }
        else
            labDescriptionController.update(null);
    }
}

/* TODO:
    Cоздать пары <TreeItem, GivenTask>, организовать TreeItemService
    для работы с деревом, покрасить само дерево в цвета статусов
      Фильтрация лаб:
      - по статусу выполнения

*
* */
        /*
        if (!resultList.isEmpty()) {
            TreeItem<String> labItem = new TreeItem<>(labName);
            resultList.get(0).getChildren().add(labItem);
        }
        labTree.getRoot().getChildren().add(new TreeItem<>(labName));
         */


   /* public void requestLoadConfig(JsonObject config) {
        labListPageModel.loadConfig(config);
    }

    public void requestAddThemeToTreeView(String themeName, String parentName) {
        TreeItem<String> sectionParent = labTree.getRoot().getChildren().stream().filter(treeItem ->
            treeItem.getValue().equals(parentName)).findFirst().get();
        System.out.println(parentName);
        sectionParent.getChildren().add(new TreeItem<>(themeName));
        //TreeItem<String> item = new TreeItem<>(themeName);
        //labTree.getRoot().getChildren().add(item);
    }*/