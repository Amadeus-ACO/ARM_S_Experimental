package client.mainWindow.pages.labPages.base.taskProgress;

import javafx.scene.control.TableView;

public class TaskProgressView {
    private final TaskProgressController taskProgressController;

    private TableView<String> tableView;

    public TaskProgressView(TaskProgressController taskProgressController) {
        this.taskProgressController = taskProgressController;
    }

    public void init(TableView<String> tableView) {
        this.tableView = tableView;
        //tableView
    }
}
