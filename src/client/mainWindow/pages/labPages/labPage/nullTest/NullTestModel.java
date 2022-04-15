package client.mainWindow.pages.labPages.labPage.nullTest;

import client.mainWindow.pages.labPages.labPage.nullTest.base.Question;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class NullTestModel {

    private NullTestController nullTestController;
    NullTestModel(NullTestController nullTestController) {
        this.nullTestController = nullTestController;
    }

    private ArrayList<Question> questionList = new ArrayList<>();
    private int currentQIndex;

    private Question.POSITION getCurrentQPositionType() {
        Question.POSITION position;
        if (currentQIndex == 0)
            position = Question.POSITION.FIRST;
        else if (currentQIndex == this.questionList.size()-1)
            position = Question.POSITION.LAST;
        else
            position = Question.POSITION.TRANSITIONAL;
        return position;
    }

    public void loadConfig(JsonObject config) {
        JsonArray questionJsonArray = config.getAsJsonArray("questionList");

        JsonObject questionWithAnswers;
        String question;
        JsonArray answerList;

        for (int qIndex = 0; qIndex < questionJsonArray.size(); qIndex++) {
            questionWithAnswers = questionJsonArray.get(qIndex).getAsJsonObject();

            question = questionWithAnswers.get("question").getAsString();
            this.questionList.add(new Question(question, "", new ArrayList<>()));

            answerList = questionWithAnswers.getAsJsonArray("answerList");
            for (JsonElement aElem: answerList) {
                this.questionList.get(qIndex).getAnswerList().add(
                        aElem.getAsJsonObject().get("text").getAsString()
                );
            }
        }

        currentQIndex = config.get("current").getAsInt();
        nullTestController.requestShowQuestion(
            currentQIndex, this.questionList.get(currentQIndex), getCurrentQPositionType()
        );
    }

    public void changeCurrentQuestion(Question.ORDER order) {
        switch (order) {
            case PREVIOUS -> {
                currentQIndex--;
                nullTestController.requestShowQuestion(
                        currentQIndex, this.questionList.get(currentQIndex), getCurrentQPositionType()
                );
            }
            case NEXT -> {
                currentQIndex++;
                nullTestController.requestShowQuestion(
                        currentQIndex, this.questionList.get(currentQIndex), getCurrentQPositionType()
                );
            }
        }
    }
}
