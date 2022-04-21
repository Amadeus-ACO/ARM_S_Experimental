package client.mainWindow.pages.labPages.labPage.nullTest;

import client.mainWindow.pages.labPages.labPage.base.stage.StageController;
import client.mainWindow.pages.labPages.labPage.nullTest.base.Question;
import com.google.gson.JsonObject;
import entity.work.test.nullTest.NullTest;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class NullTestController extends StageController {

    private StackPane root = new StackPane();

    @Override
    public StackPane getRoot() {
        return root;
    }

    private NullTestModel nullTestModel = new NullTestModel(this);
    private NullTestView nullTestView = new NullTestView(this);


    public NullTestController(JsonObject config) {
        super();

        Button previousButton = new Button("Назад");
        previousButton.setOnAction(this::onPreviousButtonClick);

        Button endTestButton = new Button("Закончить тест");
        endTestButton.setOnAction(this::onEndTestButtonClick);

        Button nextButton = new Button("Вперёд");
        nextButton.setOnAction(this::onNextButtonClick);

        // Инициализация стартовой страницы нулевого тестирования
        nullTestView.init(root, previousButton, endTestButton, nextButton);

        // Инициализация модели нулевого тестирования
        nullTestModel.loadConfig(config);
    }

    private void onEndTestButtonClick(ActionEvent actionEvent) {
    }

    private void onNextButtonClick(ActionEvent actionEvent) {
        nullTestModel.changeCurrentQuestion(Question.ORDER.NEXT);
    }

    private void onPreviousButtonClick(ActionEvent actionEvent) {
        nullTestModel.changeCurrentQuestion(Question.ORDER.PREVIOUS);
    }


    void requestShowQuestion(int qIndex, Question question,
                             Question.POSITION order) {
        //TODO: Нужно ли делать возможность выбора только одного ответа?
        ArrayList<CheckBox> answerCheckBoxList = new ArrayList<>();
        int aIndex = 0;
        for (String answer: question.getAnswerList()) {
            CheckBox answerCheckBox = new CheckBox(answer);
            int finalAIndex = aIndex;
            answerCheckBox.setOnAction(event -> onAnswerCheckBoxClick(qIndex, finalAIndex));
            answerCheckBoxList.add(answerCheckBox);
            aIndex++;
        }
        nullTestView.showQuestion(qIndex, question.getText(), answerCheckBoxList, order);

    }

    private void onAnswerCheckBoxClick(int qIndex, int aIndex) {
    }

}
