package client.mainWindow.pages.labPages.labPage.finalTest.base;

public class Question {
    private String text;
    private entity.work.test.base.Question.TYPE type;

    public Question(String text, entity.work.test.base.Question.TYPE type) {
        this.text = text;
        this.type = type;
    }

    public entity.work.test.base.Question.TYPE getType(){
        return type;
    }

    public String getText() {
        return text;
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
