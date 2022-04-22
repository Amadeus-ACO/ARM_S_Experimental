package main;

import INSTRUMENTS.EXPRESSION.MathField;
import LogisimFX.Startup;
import client.Config;
import client.loginWindow.LoginController;
import client.mainWindow.MainController;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import entity.CamelCaseNamingStrategy;
import entity.Entity;
import entity.user.Student;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Configuration
public class App extends Application {

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    private Stage primaryStage;

    private boolean loginSuccess;
    public void setLoginSuccess(boolean loginSuccess) throws IOException {
        this.loginSuccess = loginSuccess;
        //System.out.println(responseStudent.getName());

        if (loginSuccess) initMainWindow();
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

    @Bean
    public static @NotNull ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        Reflections reflections = new Reflections(Entity.class.getPackageName());
        Set<Class<? extends Entity>> subClasses = reflections.getSubTypesOf(Entity.class);
        subClasses.forEach(aClass -> objectMapper.registerSubtypes(new NamedType(aClass, aClass.getSimpleName())));
        objectMapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        // TODO: По возможности избавиться от этого
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        return objectMapper;
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
    private void initMainWindow() throws IOException {
        Student requestStudent = new Student();
        requestStudent.setId(UUID.fromString("1e99dd7d-c2e5-4550-b0d6-175c1b0e2d33"));
        Student responseStudent = null;
        try {
            responseStudent = Web.getStudentData(requestStudent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("main_window/mainWindow.fxml")
            )
        );
        fxmlLoader.setController(MainController.getInstance());
        Parent root = fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();
        mainController.requestUpdateModel(responseStudent);
        mainController.requestLoadConfig(Config.load("oneTabPane")); // Запрос на парсинг конфигурации основного окна
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