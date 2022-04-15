package client.mainWindow.pages.labPages.labPage.nullTest.base;

import java.util.ArrayList;

public class Question {
    private String text;
    private String type;
    private ArrayList<String> answerList;

    public Question(String text, String type, ArrayList<String> answerList) {
        this.text = text;
        this.type = type;
        this.answerList = answerList;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getAnswerList() {
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
