package client.loginWindow;

import entity.user.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.App;
import main.Web;

import java.io.IOException;
import java.net.URL;
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

    @FXML
    private ProgressBar loadProgressBar;

    @FXML
    private Label loadStatusLabel;

    public static Student student;

    @FXML
    void onLoginButtonAction(ActionEvent event) throws IOException {
        loginModel.setLogin(loginField.getText());
        loginModel.setPassword(passwordField.getText());

        try {
            student = Web.Auth(loginField.getText(), passwordField.getText());
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка авторизации");
            alert.setHeaderText("Ошибка авторизации");
            alert.setContentText("Авторизация не удалась");
            alert.showAndWait();
            return;
        }
        app.requestOpenMainWindow(student);
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

        loadProgressBar.setVisible(false);
        loadStatusLabel.setVisible(false);
    }

    public void setApp(App app) {
        this.app = app;
    }
}