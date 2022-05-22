package main;

import INSTRUMENTS.EXPRESSION.MathField;
import client.Config;
import client.loginWindow.LoginController;
import client.mainWindow.MainController;
import entity.user.Student;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Configuration
public class App extends Application {

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    private Stage primaryStage;

    private boolean loginSuccess;
    public void requestOpenMainWindow(Student student) throws IOException {
        //System.out.println(responseStudent.getName());
        initMainWindow(student);

    }

    public static ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
            ac.registerBean(Application.class, () -> App.this);
            ac.registerBean(Parameters.class, this::getParameters);
            ac.registerBean(HostServices.class, this::getHostServices);
        };
        this.context = new SpringApplicationBuilder()
                .sources(Main.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    /**
     * Starting initialization of Application
     * @param primaryStage - primary stage of Application
     * @throws IOException - because of FXMLLoader and converting cyrillic text
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.context.publishEvent(new StageReadyEvent(primaryStage));

        this.primaryStage = primaryStage;

        // MathField Preload
        new WebEngine().load(MathField.htmlLocation);

        initLoginWindow();
        //initMainWindow();
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }

    @Getter
    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {super(stage);}
    }

    /**
     * Initialization of LoginWindow
     * @throws IOException - because of FXMLLoader and converting cyrillic text
     */
    private void initLoginWindow() throws IOException {
        // Logisim Preload
      //  Startup startup = Startup.parseArgs(new String[0]);
    //    startup.run();

        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("login_screen.fxml")));
        Parent root = fxmlLoader.load(); ((LoginController) fxmlLoader.getController()).setApp(this);

        primaryStage.hide();
        primaryStage.setResizable(false);
        primaryStage.setTitle("АСО Амадей | Вход в систему");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    /**
     * Initialization of MainWindow
     * @throws IOException - because of FXMLLoader and converting cyrillic text
     */
    private void initMainWindow(Student student) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("main_window/mainWindow.fxml")
            )
        );
        fxmlLoader.setController(MainController.getInstance());
        Parent root = fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();
        mainController.requestUpdate(student);
        mainController.requestLoadWindowConfig(Config.load("oneTabPane")); // Запрос на парсинг конфигурации основного окна
        // mainController.setApp(this);



        primaryStage.hide();
        primaryStage.setResizable(true);
        primaryStage.setTitle("АСО Амадей | Главное окно");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(800);
        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {System.exit(0);});
        primaryStage.show();
    }

    /**
     * Main
     * @param args - args
     */
    public static void main(String[] args) {
        launch(args);
    }


}