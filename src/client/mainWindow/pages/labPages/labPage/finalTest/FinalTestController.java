package client.mainWindow.pages.labPages.labPage.finalTest;

import LogisimFX.newgui.FrameManager;
import client.mainWindow.pages.labPages.labPage.base.stage.StageController;

import client.mainWindow.pages.labPages.labPage.finalTest.base.Question;
import entity.work.test.base.Answer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class FinalTestController extends StageController {

    private FinalTestModel finalTestModel = new FinalTestModel(this);
    private FinalTestView finalTestView = new FinalTestView(this);

    private StackPane root = new StackPane();

    public FinalTestController() {
        super();

        Button previousButton = new Button("Назад");
        previousButton.setOnAction(this::onPreviousButtonClick);

        Button endTestButton = new Button("Закончить тест");
        endTestButton.setOnAction(this::onEndTestButtonClick);

        Button nextButton = new Button("Вперёд");
        nextButton.setOnAction(this::onNextButtonClick);

        // Инициализация стартовой страницы нулевого тестирования
        finalTestView.init(root, previousButton, endTestButton, nextButton);

        // Инициализация модели нулевого тестирования
        finalTestModel.loadConfig(finalTestModel.fakeFinalTest);
    }

    @Override
    public StackPane getRoot() {
        return root;
    }

    private void onEndTestButtonClick(ActionEvent actionEvent) {
    }

    private void onNextButtonClick(ActionEvent actionEvent) {
        finalTestModel.changeCurrentQuestion(Question.ORDER.NEXT);
    }

    private void onPreviousButtonClick(ActionEvent actionEvent) {
        finalTestModel.changeCurrentQuestion(Question.ORDER.PREVIOUS);
    }


    void requestShowQuestion(int qIndex, Question question,
                             Question.POSITION order) {
        //TODO: Нужно ли делать возможность выбора только одного ответа?

        Parent root = null;

        int aIndex = 0;
        if(question.getType() == entity.work.test.base.Question.TYPE.FINAL_TEXT){
            root = new TextArea();
        }else if(question.getType() == entity.work.test.base.Question.TYPE.FINAL_AOVT){
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("aovt_start.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(question.getType() == entity.work.test.base.Question.TYPE.FINAL_LOVT){
            root = FrameManager.scene.getRoot();
        }

        finalTestView.showQuestion(qIndex, question.getText(), root, order);
/*
        for (Answer answer: question.get()) {
            CheckBox answerCheckBox = new CheckBox(answer.getText());
            int finalAIndex = aIndex;
            if(question.getType() == entity.work.test.base.Question.TYPE.NULL_ONE_ANSWER){
                answerCheckBox.setOnAction(event -> {
                    for(CheckBox checkBox: answerCheckBoxList){
                        if(checkBox != answerCheckBox) checkBox.setSelected(false);
                    }
                    onAnswerCheckBoxClick(qIndex, finalAIndex);
                });
            }else if(question.getType() == entity.work.test.base.Question.TYPE.NULL_MULTIPLE_ANSWER) {
                answerCheckBox.setOnAction(event -> onAnswerCheckBoxClick(qIndex, finalAIndex));
            }
            answerCheckBoxList.add(answerCheckBox);
            aIndex++;
        }

 */


    }


}