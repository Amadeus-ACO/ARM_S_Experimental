package client.mainWindow.pages.labPages.labPage.finalTest;

import client.mainWindow.pages.labPages.labPage.finalTest.base.Question;
import entity.work.test.base.Answer;
import entity.work.test.finalTest.FinalAnswer;
import entity.work.test.finalTest.FinalTest;
import entity.work.test.nullTest.NullAnswer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FinalTestModel {

    private FinalTestController finalTestController;

    FinalTestModel(FinalTestController finalTestController) {

        this.finalTestController = finalTestController;

        fakeFinalTest = new FinalTest();

        List<FinalAnswer> finalAnswers = new ArrayList<>();

        FinalAnswer finalAnswer1 = new FinalAnswer();

        entity.work.test.base.Question question1 = new entity.work.test.base.Question();
        question1.setType(entity.work.test.base.Question.TYPE.FINAL_TEXT);
        question1.setText("Алгоритм перевода чисел из одной системы счисления в другую");

        finalAnswer1.setGivenQuestion(question1);

        FinalAnswer finalAnswer2 = new FinalAnswer();

        entity.work.test.base.Question question2 = new entity.work.test.base.Question();
        question2.setType(entity.work.test.base.Question.TYPE.FINAL_AOVT);
        question2.setText("10(10) + 101(2) = ?(16)");

        finalAnswer2.setGivenQuestion(question2);


        FinalAnswer finalAnswer3 = new FinalAnswer();

        entity.work.test.base.Question question3 = new entity.work.test.base.Question();
        question3.setType(entity.work.test.base.Question.TYPE.FINAL_LOVT);
        question3.setText("Построить дешифратор на элементах И-НЕ");

        finalAnswer3.setGivenQuestion(question3);

        finalAnswers.add(finalAnswer1);
        finalAnswers.add(finalAnswer2);
        finalAnswers.add(finalAnswer3);

        fakeFinalTest.setFinalAnswerList(finalAnswers);

    }

    public FinalTest fakeFinalTest;
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

    public void loadConfig(FinalTest finalTest) {

        String question;

        for(FinalAnswer finalAnswer: finalTest.getFinalAnswerList()){

            question = finalAnswer.getGivenQuestion().getText();
            this.questionList.add(new Question(question, finalAnswer.getGivenQuestion().getType()));
        }

        finalTestController.requestShowQuestion(
                currentQIndex, this.questionList.get(currentQIndex), getCurrentQPositionType()
        );

    }

    public void changeCurrentQuestion(Question.ORDER order) {
        switch (order) {
            case PREVIOUS -> {
                currentQIndex--;
                finalTestController.requestShowQuestion(
                        currentQIndex, this.questionList.get(currentQIndex), getCurrentQPositionType()
                );
            }
            case NEXT -> {
                currentQIndex++;
                finalTestController.requestShowQuestion(
                        currentQIndex, this.questionList.get(currentQIndex), getCurrentQPositionType()
                );
            }
        }
    }

}
