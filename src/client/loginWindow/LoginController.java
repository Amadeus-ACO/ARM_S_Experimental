package client.loginWindow;

import client.mainWindow.sectionMenu.labs.LabView;
import entity.user.Student;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import main.App;
import main.Main;
import main.Web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberPasswordCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    public static Student student;

    @FXML
    void onLoginButtonAction(ActionEvent event) throws IOException {
        loginModel.setLogin(loginField.getText());
        loginModel.setPassword(passwordField.getText());

        try {
            student = Web.Auth(loginField.getText(), passwordField.getText());
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка авторизации");
            alert.setHeaderText("Ошибка авторизации");
            alert.setContentText("Ты обосрался в авторизации");
        }

        app.setLoginSuccess(true);
    }

    private App app;
    private LoginView loginView;
    private LoginModel loginModel;

//    public LoginController(App app) throws IOException {
//        this.app = app;
//        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("login_screen.fxml")));
//        //fxmlLoader.setController(this); Parent root = fxmlLoader.load();
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login_screen.fxml")));
//        this.app.getPrimaryStage().setResizable(false);
//        this.app.getPrimaryStage().setTitle(App.getCyrillicText("АСО Амадей | Вход в систему"));
//        this.app.getPrimaryStage().setScene(new Scene(root, 400, 300));
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loginView = new LoginView();
        this.loginModel = new LoginModel();
    }



    public void setApp(App app) {
        this.app = app;
    }
}