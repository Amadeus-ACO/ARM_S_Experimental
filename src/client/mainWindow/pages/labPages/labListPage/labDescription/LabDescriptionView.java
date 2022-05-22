package client.mainWindow.pages.labPages.labListPage.labDescription;

import client.ColorPalette;
import entity.work.GivenTask;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.util.Pair;

import java.util.Optional;

public class LabDescriptionView {

    @FXML
    Label statusLabel;

    @FXML
    VBox mainVBox;

    @FXML
    SplitPane mainSplitPane;

    @FXML
    TreeView<String> stepTreeView;

    @FXML
    TableView<Pair<String, String>> resultTableView;

    @FXML
    Button startLabButton;

    @FXML
    GridPane formulationProblemGridPane;

    @FXML
    WebView formulationProblemWebView;

    @FXML
    SplitPane progressSplitPane;

    @FXML
    GridPane labProgressGridPane;

    @FXML
    HBox buttonPane;

    @FXML
    Label startDateLabel;

    @FXML
    Label finishDateLabel;

    @FXML
    Label gradeLabel;

    @FXML
    Button commentButton;

    @FXML
    Button TaskHelpBtn;
    WebView TaskHelpWebView;

    private BoxBlur boxBlur;

    protected void resetToDefault() {
        mainVBox.setVisible(true);

        formulationProblemGridPane.setDisable(false);
        progressSplitPane.setDisable(false);
        buttonPane.setDisable(false);

        labProgressGridPane.setVisible(false);
        formulationProblemWebView.setEffect(null);

        startLabButton.setDisable(false);
        commentButton.setDisable(true);

        startDateLabel.setVisible(true);
        finishDateLabel.setVisible(false);
        gradeLabel.setVisible(false);
    }

    protected void updateView(GivenTask.COMPLETE_STATUS completeStatus) {
        resetToDefault();
        if (completeStatus == null)
            mainVBox.setVisible(false);
        else {
            switch (completeStatus) {
                case GIVEN, IN_WORK -> {}
                case ON_FIX -> {
                    commentButton.setDisable(false);
                }
                case WAITING_FOR_CHECK, WAITING_FOR_FINAL_TEST_CHECK -> {
                    formulationProblemGridPane.setDisable(true);
                    progressSplitPane.setDisable(true);
                    buttonPane.setDisable(true);

                    formulationProblemWebView.setEffect(boxBlur);
                }
                case COMPLETED -> {
                    progressSplitPane.setDisable(true);
                    startLabButton.setDisable(true);
                    finishDateLabel.setVisible(true);
                    gradeLabel.setVisible(true);
                }
            }
            statusLabel.setBackground(new Background(new BackgroundFill(
                    ColorPalette.getCompleteStatusColor(completeStatus),
                    new CornerRadii(10),
                    new Insets(0)))
            );
            statusLabel.setBorder(new Border(new BorderStroke(
                    Color.DIMGRAY,
                    BorderStrokeStyle.SOLID,
                    new CornerRadii(10),
                    new BorderWidths(3)))
            );
        }
    }

    protected void init() {
        this.boxBlur = new BoxBlur();
        boxBlur.setWidth(10);
        boxBlur.setHeight(5);
        boxBlur.setIterations(3);
    }
}



        /*switch (visibility) {
            case VISIBLE -> {
                mainVBox.setVisible(true);

                formulationProblemGridPane.setDisable(false);
                progressSplitPane.setDisable(false);
                buttonPane.setDisable(false);

                formulationProblemWebView.setEffect(null);
            }
            case BLUR -> {
                mainVBox.setVisible(true);

                formulationProblemGridPane.setDisable(true);
                progressSplitPane.setDisable(true);
                buttonPane.setDisable(true);

                formulationProblemWebView.setEffect(boxBlur);
            }
            case NONE -> {
                mainVBox.setVisible(false);
            }
*/
        /*Rectangle rectangle = new Rectangle();
        rectangle.setStroke(Color.DIMGRAY);
        rectangle.setStrokeWidth(1);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.setWidth(150);
        rectangle.setHeight(20);

        rectangle.setFill(switch (completeStatus) {
            case COMPLETED -> Color.LIGHTGREEN;
            case ON_FIX -> Color.LIGHTPINK;
            case IN_WORK -> Color.LIGHTCYAN;
            case WAITING_FOR_CHECK -> Color.LEMONCHIFFON;
            default -> Color.LIGHTGRAY;
        });

        statusLabel.setGraphic(rectangle);
        statusLabel.setGraphicTextGap(0);*/


