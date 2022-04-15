package INSTRUMENTS.EXPRESSION.debug;

import INSTRUMENTS.EXPRESSION.MathField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class DebugApp extends Application {

    public static final int fieldRestriction = 15;  // Ограничение по количеству символов
    public static final int fieldWidth = 130;       // Ширина поля ввода

    public static final int webViewEditWidth = 500; // Ширина всего пространства при редактировании (по умолчанию: 260)
    public static final int webViewSaveWidth = 500; // Ширина всего пространства при сохранении (по умолчанию: 140)
    public static final int webViewHeight = 600;    // Высота всего пространства (по умолчанию: 350)

    @Override
    public void start(Stage primaryStage) throws Exception {
        MathField mathField = new MathField();
        mathField.setEditMode(true);

        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(mathField.getWebView(), mathField.getEditButton());

        Stage stage = new Stage();
        Scene scene = new Scene(flowPane);
        stage.setScene(scene);
        stage.show();

        mathField.getWebView().getEngine().documentProperty().addListener(((observable, oldValue, newValue) -> {

            // Установка параметров ширины и высоты
            mathField.setFieldRestriction(fieldRestriction);
            mathField.setFieldWidth(fieldWidth);
            mathField.setWebViewEditWidth(webViewEditWidth);
            mathField.setWebViewSaveWidth(webViewSaveWidth);
            mathField.setWebViewHeight(webViewHeight);

            // Добавление FireBug
            mathField.getWebView().getEngine().executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://web.archive.org/web/20181231025557/https://getfirebug.com/firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://web.archive.org/web/20181231025557/https://getfirebug.com/' + '#startOpened');}");

            // Обновление размеров окна
            stage.sizeToScene();
        }));
    }

    public static void runApp(String[] args) {
        launch(args);
    }
}
