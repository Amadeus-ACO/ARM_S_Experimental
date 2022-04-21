package client.mainWindow.pages.labPages.labPage.finalTest;

import client.mainWindow.pages.labPages.labPage.finalTest.base.Question;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class FinalTestView {

    private FinalTestController finalTestController;

    private StackPane root;
    private Label questionLabel = new Label();
    private VBox questionWithAnswersVBox = new VBox();
    private HBox buttonHBox = new HBox();

    private Button previousButton;
    private Button endTestButton;
    private Button nextButton;


    FinalTestView(FinalTestController finalTestController) {this.finalTestController = finalTestController;}

    public void init(StackPane root, Button previousButton, Button endTestButton, Button nextButton) {
        this.root = root;
        this.previousButton = previousButton;
        this.endTestButton = endTestButton;
        this.nextButton = nextButton;

        // Настройка панели с вопросами и ответами
        StackPane.setAlignment(questionWithAnswersVBox, Pos.CENTER);
        questionWithAnswersVBox.setSpacing(5);
        questionWithAnswersVBox.setMaxWidth(Region.USE_PREF_SIZE);
        questionWithAnswersVBox.setMaxHeight(Region.USE_PREF_SIZE);
        questionWithAnswersVBox.getChildren().add(questionLabel);
        /*StackPane QAStackPane = new StackPane();
        StackPane.setAlignment(questionWithAnswersVBox, Pos.CENTER);
        QAStackPane.getChildren().add(questionWithAnswersVBox);
        VBox.setVgrow(QAStackPane, Priority.ALWAYS);*/
        StackPane.setAlignment(previousButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(previousButton, new Insets(20));
        StackPane.setAlignment(endTestButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(endTestButton, new Insets(20));
        StackPane.setAlignment(nextButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(nextButton, new Insets(20));
        // Настройка панели с кнопками
        // VBox.setVgrow(buttonHBox, Priority.NEVER);
        //StackPane.setAlignment(buttonHBox, Pos.BOTTOM_CENTER);
        //buttonHBox.getChildren().addAll();

        // Настройка корневого узла
        VBox.setVgrow(root, Priority.ALWAYS);
        root.getChildren().addAll(questionWithAnswersVBox, previousButton, endTestButton, nextButton);
    }


    public void showQuestion(int qIndex, String qText,
                             Parent subroot,
                             Question.POSITION order) {
        questionLabel.setText(qIndex+1 + ". " + qText);

        questionWithAnswersVBox.getChildren().clear();
        questionWithAnswersVBox.getChildren().add(questionLabel);
        questionWithAnswersVBox.getChildren().addAll(subroot);

        previousButton.setDisable(false);
        nextButton.setDisable(false);
        if (order == Question.POSITION.FIRST)
            previousButton.setDisable(true);
        else if (order == Question.POSITION.LAST)
            nextButton.setDisable(true);
    }

}
