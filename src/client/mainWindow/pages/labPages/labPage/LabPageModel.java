package client.mainWindow.pages.labPages.labPage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LabPageModel {


    static final String NULL_TEST = "nullTest";
    static final String ALGORITHM = "algorithm";
    static final String FINAL_TEST = "finalTest";
    static final String REPORT = "report";

    private final LabPageController labPageController;
    LabPageModel(LabPageController labPageController) {
        this.labPageController = labPageController;
    }

    public void loadConfig(JsonObject config) {
        JsonArray stageJsonArray = config.getAsJsonArray("stageList");

        JsonObject stageJsonObject, stageConfig;
        String name, type;
        boolean passed;

        for(JsonElement stageJsonElement : stageJsonArray) {
            stageJsonObject = stageJsonElement.getAsJsonObject();
            name = stageJsonObject.get("name").getAsString();
            type = stageJsonObject.get("type").getAsString();
            passed = stageJsonObject.get("passed").getAsBoolean();
            stageConfig = stageJsonObject.getAsJsonObject("config");

            labPageController.requestAddNewStage(name, type, passed, stageConfig);
        }
    }
}
