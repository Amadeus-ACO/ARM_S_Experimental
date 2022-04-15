package client.mainWindow.pages.labPages.labListPage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LabListPageModel {

    private LabListPageController labListPageController;

    public LabListPageModel(LabListPageController labListPageController) {
        this.labListPageController = labListPageController;
    }

    public void loadConfig(JsonObject config) {
        JsonArray themeList = config.getAsJsonArray("themeList");
        JsonObject themeObject, labObject;
        String themeName, labName;

        // Добавление темы
        for (JsonElement themeElem: themeList) {
            themeObject = themeElem.getAsJsonObject();
            themeName = themeObject.get("name").getAsString();
            labListPageController.requestAddThemeToTreeView(themeName);

            // Добавление лабораторной работы
            for (JsonElement labElem: themeObject.get("labList").getAsJsonArray()) {
                labObject = labElem.getAsJsonObject();
                labName = labObject.get("name").getAsString();
                labListPageController.requestAddLabToTreeView(themeName, labName);
            }

        }
    }



}
