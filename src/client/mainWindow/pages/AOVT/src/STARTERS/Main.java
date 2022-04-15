/*
 * Copyright (c) amadeus-aco.ru
 */

package STARTERS;

import INSTRUMENTS.EXPRESSION.MathField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("aovt_start.fxml"));
        primaryStage.setTitle("amadeus-aco.ru aovt ver. 0.1.0");
        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add("styles.css");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("icon.jpg"));
        primaryStage.show();

        Platform.runLater(() -> {
            new WebEngine().load(MathField.htmlLocation);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}

/*TODO:
   1. Оффсеты по краям для шагов и инструментов
   2. Смена значка (заменить на другой при перетаскивании)
   3.
 */