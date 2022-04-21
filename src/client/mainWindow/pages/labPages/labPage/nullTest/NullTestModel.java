package client.mainWindow.pages.labPages.labPage.nullTest;

import client.mainWindow.pages.labPages.labPage.nullTest.base.Question;

import entity.work.test.base.Answer;
import entity.work.test.nullTest.NullAnswer;
import entity.work.test.nullTest.NullTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NullTestModel {

    private NullTestController nullTestController;

    NullTestModel(NullTestController nullTestController) {

        this.nullTestController = nullTestController;

        fakeNullTest = new NullTest();

        List<NullAnswer> nullAnswers = new ArrayList<>();

        NullAnswer nullAnswer1 = new NullAnswer();

        entity.work.test.base.Question question1 = new entity.work.test.base.Question();
        question1.setType(entity.work.test.base.Question.TYPE.NULL_ONE_ANSWER);
        question1.setText("Сколько адресных входов у мультиплексора 8-1?");

        Answer answer1 = new Answer();
        answer1.setText("1");
        answer1.setRightFlag(false);

        Answer answer2 = new Answer();
        answer2.setText("2");
        answer2.setRightFlag(false);

        Answer answer3 = new Answer();
        answer3.setText("3");
        answer3.setRightFlag(true);

        Answer answer4 = new Answer();
        answer4.setText("4");
        answer4.setRightFlag(false);

        question1.setAnswerList(new ArrayList<>(Arrays.asList(answer1, answer2, answer3, answer4)));

        nullAnswer1.setGivenQuestion(question1);


        NullAnswer nullAnswer2 = new NullAnswer();

        entity.work.test.base.Question question2 = new entity.work.test.base.Question();
        question2.setType(entity.work.test.base.Question.TYPE.NULL_MULTIPLE_ANSWER);
        question2.setText("Какой вид представления двоичных чисел существует?");

        answer1 = new Answer();
        answer1.setText("Прямой");
        answer1.setRightFlag(true);

        answer2 = new Answer();
        answer2.setText("Обратный");
        answer2.setRightFlag(true);

        answer3 = new Answer();
        answer3.setText("Дополнительный");
        answer3.setRightFlag(true);

        answer4 = new Answer();
        answer4.setText("Мнимый");
        answer4.setRightFlag(false);

        question2.setAnswerList(new ArrayList<>(Arrays.asList(answer1, answer2, answer3, answer4)));

        nullAnswer2.setGivenQuestion(question2);


        nullAnswers.add(nullAnswer1);
        nullAnswers.add(nullAnswer2);

        fakeNullTest.setNullAnswerList(nullAnswers);

    }

    public NullTest fakeNullTest;
    private ArrayList<Question> questionList = new ArrayList<>();
    private int currentQIndex = 0;

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

    public void loadConfig(NullTest nullTest) {

        String question;

        for(NullAnswer nullAnswer: nullTest.getNullAnswerList()){

            question = nullAnswer.getGivenQuestion().getText();
            this.questionList.add(new Question(question, nullAnswer.getGivenQuestion().getType(),
                    new ArrayList<>(nullAnswer.getGivenQuestion().getAnswerList())));
        }

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
