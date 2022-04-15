package client.mainWindow;

import com.google.gson.JsonObject;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainView {

    /**
     * Создание окна для нового менеджера панелей вкладок
     *
     * @param stageProps - конфигурация окна
     * @param tabManager - таблица панелей вкладок
     */
    void createTabStage(JsonObject stageProps, BorderPane tabManager) {
        // Создание окна
        Stage stage = new Stage();

        // Выбор монитора и установка позиции окна для менеджера
        Screen screen = Screen.getScreens().get(stageProps.get("screen").getAsInt()-1);
        stage.setX(screen.getBounds().getMinX() + stageProps.get("x").getAsDouble());
        stage.setY(screen.getBounds().getMinY() + stageProps.get("y").getAsDouble());

        // Создание сцены
        Scene scene = new Scene(tabManager);
        stage.setScene(scene);
        stage.show();
    }
}
