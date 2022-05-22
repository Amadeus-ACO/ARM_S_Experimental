package client.mainWindow.widgetPanel;

import lombok.Getter;

@Getter
public class WidgetPanelModel {

    private String studentName;
    private String studentGroupName;

    public void update(String studentName, String studentGroupName) {
        this.studentName = studentName;
        this.studentGroupName = studentGroupName;
    }
}
