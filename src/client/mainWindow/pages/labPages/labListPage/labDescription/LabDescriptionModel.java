package client.mainWindow.pages.labPages.labListPage.labDescription;

import entity.work.GivenTask;
import lombok.Getter;

public class LabDescriptionModel {
    @Getter
    private GivenTask givenTask;

    public LabDescriptionModel(LabDescriptionController labDescriptionController) {

    }

    public void update(GivenTask givenTask) {
        this.givenTask = givenTask;
    }
}
