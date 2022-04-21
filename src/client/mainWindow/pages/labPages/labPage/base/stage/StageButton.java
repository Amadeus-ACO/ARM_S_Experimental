package client.mainWindow.pages.labPages.labPage.base.stage;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;

public class StageButton extends Button {
    public StageButton(String title,  Parent stageRoot) {
        super(title);
        this.setMaxWidth(Double.MAX_VALUE);

    }
}
