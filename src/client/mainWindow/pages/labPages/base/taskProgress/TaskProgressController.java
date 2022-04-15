package client.mainWindow.pages.labPages.base.taskProgress;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;

public class TaskProgressController {

    private TaskProgressModel taskProgressModel = new TaskProgressModel(this);
    private TaskProgressView taskProgressView = new TaskProgressView(this);

    TableView tableView;

    public TableView getRoot() {
        return tableView;
    }

    public TaskProgressController() {
        Label taskProgressLabel = new Label("Текущий прогресс");
        taskProgressLabel.setFont(Font.font("Lucida Sans", 25));

        tableView = new TableView<String>();

        taskProgressView.init(tableView);


    }
}
