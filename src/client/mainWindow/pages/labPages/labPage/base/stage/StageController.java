package client.mainWindow.pages.labPages.labPage.base.stage;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public abstract class StageController {
    protected ScrollPane root;
    public StageController() {
        root = new ScrollPane();
        root.setFitToWidth(true);
        root.setFitToHeight(true);
        VBox.setVgrow(root, Priority.ALWAYS);
        root.setMaxHeight(Region.USE_COMPUTED_SIZE);
        root.setContent(new Label(getClass().getName()));
    }

    public Region getRoot() {
        return root;
    }

}
