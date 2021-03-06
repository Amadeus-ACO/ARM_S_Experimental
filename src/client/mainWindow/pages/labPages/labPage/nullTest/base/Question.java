package client.mainWindow.pages.labPages.labPage.nullTest.base;

import entity.work.test.base.Answer;

import java.util.ArrayList;

public class Question {
    private String text;
    private entity.work.test.base.Question.TYPE type;
    private ArrayList<Answer> answerList;

    public Question(String text, entity.work.test.base.Question.TYPE type, ArrayList<Answer> answerList) {
        this.text = text;
        this.type = type;
        this.answerList = answerList;
    }

    public entity.work.test.base.Question.TYPE getType(){
        return type;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Answer> getAnswerList() {
        return answerList;
    }

    public enum POSITION {
        FIRST,
        TRANSITIONAL,
        LAST,
    }
    public enum ORDER {
        PREVIOUS,
        NEXT
    }
}
