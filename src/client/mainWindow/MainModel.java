package client.mainWindow;

import client.mainWindow.roadMap.RoadMapModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.user.Student;
import lombok.Getter;

public class MainModel {
    private final MainController mainController;

    @Getter
    private Student student;

    /**
     * Загрузка конфигурации и настройка главной страницы по конфигурации
     * @param mainController
     */
    MainModel(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Предзагрузка данных с серверной части
     */
    void preloadData() {
        RoadMapModel roadMapModel = new RoadMapModel();
    }

    /**
     * Загрузка конфигурации и настройка главной страницы по конфигурации
     * @param config - конфигурация
     */
    void loadConfig(JsonObject config) {
        initTabManagers(config.getAsJsonArray("tabManagerList"));
        // initWidgets();
        // initSthElse....
    }

    /**
     * Инициализация менеджеров панелей вкладок
     * @param tabManagerList - список конфигураций менеджеров панелей вкладок
     */
    void initTabManagers(JsonArray tabManagerList) {

        // Получение конфигурации главного менеджера панелей вкладок
        JsonObject tabManagerConfig = null;
        for (JsonElement elem: tabManagerList)
            if (((JsonObject) elem).get("name").getAsString().equals("main"))
                tabManagerConfig = (JsonObject) elem;

        // Конфигурация главного менеджера найдена
        if (tabManagerConfig != null) {
            // Отправка запроса на установку конфигурации контроллеру
            mainController.requestSetConfigMainTabManager(tabManagerConfig);
            tabManagerList.remove(tabManagerConfig);
        }
        else
            // Выбрасывание исключения об отсутствии конфигурации
            throw new NullPointerException("There is no MainTabManager configuration in config entity.file");

        // Парсинг конфигураций дополнительных менеджеров панелей вкладок
        if (tabManagerList.size() != 0) {

            for (JsonElement elem : tabManagerList) {
                tabManagerConfig = (JsonObject) elem;

                // Отправка запроса на создание менеджера панелей вкладок
                mainController.requestCreateTabManager(tabManagerConfig);
            }
        }
    }

    public void update(Student student) {
        this.student = student;
    }
}
