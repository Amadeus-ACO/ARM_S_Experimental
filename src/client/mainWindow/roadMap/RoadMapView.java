package client.mainWindow.roadMap;

import com.sun.javafx.scene.control.Properties;
import entity.work.GivenTask;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;

public class RoadMapView {

    @FXML
    private HBox circleBox;

    /**
     * Аттрибуты
     */
    private ArrayList<Circle> circleList;

    /**
     * Конструктор
     * @param
     */
    public RoadMapView() {
    }

    /**
     *
     * @param circleList
     */
    public void setCircleList(ArrayList<Circle> circleList) {
        this.circleList = circleList;
    }

    public ArrayList getCircleList() {
        return circleList;
    }

    public void delete() {
    }

    public void updateCircles() {

    }

    public void drawTaskCircle(Pair<GivenTask, Button> taskCircle) {
        GivenTask givenTask = taskCircle.getKey();

        Button button = taskCircle.getValue();
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setStyle("-fx-background-color: transparent");
        //button.setPadding(Insets.EMPTY);

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