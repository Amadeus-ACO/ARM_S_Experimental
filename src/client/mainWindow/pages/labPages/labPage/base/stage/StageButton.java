package client.mainWindow.pages.labPages.labPage.base.stage;

import javafx.scene.control.Button;
import javafx.scene.layout.Region;

public class StageButton extends Button {
    public StageButton(String title, boolean passed,  Region stageRoot) {
        super(title);
        this.setMaxWidth(Double.MAX_VALUE);

    }
}
