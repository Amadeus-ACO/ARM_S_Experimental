package client.mainWindow.roadMap;

import client.ColorPalette;
import entity.work.GivenTask;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

public class RoadMapView {

    @FXML
    private HBox rectangleBox;

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
        if (!rectangleBox.getChildren().isEmpty()) {
            double circleScale = deltaY > 0 ? 0.1 : -0.1;
            rectangleBox.setScaleX(rectangleBox.getScaleX() + circleScale);
            rectangleBox.setScaleY(rectangleBox.getScaleY() + circleScale);

            scrollPane.setMinSize(scrollPane.getWidth(), scrollPane.getHeight()*(1 + circleScale));
        }
        rectangleBox.requestLayout();
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

    public void drawTaskRectangle(Pair<GivenTask, Button> taskRectangle) {
        GivenTask givenTask = taskRectangle.getKey();

        Button button = taskRectangle.getValue();
        button.setContentDisplay(ContentDisplay.CENTER);
        button.setStyle("-fx-background-color: transparent");

        button.setText(givenTask.getTaskVariant().getTask().getName());
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFont(Font.font("System",
                FontWeight.BOLD, FontPosture.REGULAR, 14));

        Rectangle rectangle = (Rectangle) button.getGraphic();
        rectangle.setStroke(Color.DIMGRAY);
        rectangle.setStrokeWidth(5);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        rectangle.setWidth(207);
        rectangle.setHeight(40);
        rectangle.setFill(ColorPalette.getCompleteStatusColor(givenTask.getCompleteStatus()));

        if (!rectangleBox.getChildren().isEmpty()) {
            Line line = new Line();
            line.setStroke(Color.DIMGRAY);
            line.setStrokeWidth(7);
            //line.setStartX(-100);
            //line.setEndX(-80);

            rectangleBox.getChildren().add(line);
        }
        rectangleBox.getChildren().add(button);

        Tooltip tooltip = button.getTooltip();
        Label labLabel = new Label(givenTask.getTaskVariant().getTask().getName());
        //labLabel.setAlignment(Pos.CENTER);
        Label shortDescLabel = new Label(givenTask.getTaskVariant().getTask().getShortDescription());
        //shortDescLabel.setWrapText(true);
        //shortDescLabel.setAlignment(Pos.CENTER);

        ((VBox) tooltip.getGraphic()).getChildren().addAll(labLabel, shortDescLabel);
    }
}