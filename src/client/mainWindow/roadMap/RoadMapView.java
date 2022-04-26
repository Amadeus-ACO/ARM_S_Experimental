package client.mainWindow.roadMap;

import entity.work.GivenTask;
import entity.work.task.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

public class RoadMapView {

    @FXML
    private HBox circleBox;

    protected ScrollPane scrollPane;

    /**
     * Конструктор
     * @param
     */
    public RoadMapView() {
    }

    /**
     *
     * @param //circleList
     */
    /*public void setCircleList(ArrayList<Circle> circleList) {
        this.circleList = circleList;
    }

    public ArrayList getCircleList() {
        return circleList;
    }

    public void delete() {
    }

    public void updateCircles() {

    }*/

    public void zoom(double deltaY) {
        System.out.println(deltaY);
        if (!circleBox.getChildren().isEmpty()) {
            double circleScale = deltaY > 0 ? 0.1 : -0.1;
            circleBox.setScaleX(circleBox.getScaleX() + circleScale);
            circleBox.setScaleY(circleBox.getScaleY() + circleScale);

            scrollPane.setMinSize(scrollPane.getWidth(), scrollPane.getHeight()*(1 + circleScale));
        }
        circleBox.requestLayout();
            /*for (Node node: circleBox.getChildren()) {
                System.out.println(node.getClass().getSimpleName());
                switch (node.getClass().getSimpleName()) {
                    case "Button":
                        Button circleButton = ((Button) node);
                        circleButton.setScaleX(circleButton.getScaleX() + circleScale);
                        circleButton.setScaleY(circleButton.getScaleY() + circleScale);
                        break;
                }
            }*/
        //circleBox.resize
    }

    public void drawTaskCircle(Pair<GivenTask, Button> taskCircle) {
        GivenTask givenTask = taskCircle.getKey();

        Button button = taskCircle.getValue();
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setStyle("-fx-background-color: transparent");

        button.setText(givenTask.getTaskVariant().getTask().getNumber());
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 18));

        Circle circle = (Circle) button.getGraphic();
        circle.setStroke(Color.DIMGRAY);
        circle.setStrokeWidth(7);
        circle.setRadius(25);
        circle.setFill(switch (givenTask.getCompleteStatus()) {
            case COMPLETED -> Color.LIGHTGREEN;
            case ON_FIX -> Color.LIGHTPINK;
            case IN_WORK -> Color.LIGHTCYAN;
            case WAITING_FOR_CHECK -> Color.YELLOW;
            default -> Color.LIGHTGRAY;
        });

        if (!circleBox.getChildren().isEmpty()) {
            Line line = new Line();
            line.setStroke(Color.DIMGRAY);
            line.setStrokeWidth(7);
            //line.setStartX(-100);
            //line.setEndX(-80);

            circleBox.getChildren().add(line);
        }
        circleBox.getChildren().add(button);

        Tooltip tooltip = button.getTooltip();
        Label labLabel = new Label(givenTask.getTaskVariant().getTask().getName());
        //labLabel.setAlignment(Pos.CENTER);
        Label shortDescLabel = new Label(givenTask.getTaskVariant().getTask().getShortDescription());
        //shortDescLabel.setWrapText(true);
        //shortDescLabel.setAlignment(Pos.CENTER);

        ((VBox) tooltip.getGraphic()).getChildren().addAll(labLabel, shortDescLabel);
    }
}