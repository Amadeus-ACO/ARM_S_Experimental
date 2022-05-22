package client.mainWindow.roadMap;

import entity.user.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import entity.work.GivenTask;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import main.Web;

import java.util.*;

/**
 * Класс-контроллер дорожной карты
 */
public class RoadMapController extends RoadMapView {

    @FXML
    private HBox rectangleBox;

    /**
     * Аттрибуты класса
     */
    private RoadMapModel roadMapModel = new RoadMapModel();
    private List<Pair<GivenTask, Button>> rectangleTaskList;

    public void setRoadMapModel(RoadMapModel roadMapModel) {
        this.roadMapModel = roadMapModel;
    }

    private Button createTaskRectangle(GivenTask task) {
        Button button = new Button();
        button.setGraphic(new Rectangle());
        Tooltip tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setHideDelay(Duration.millis(100));
        tooltip.setGraphic(new VBox());
        button.setTooltip(tooltip);
        return button;
    }

    public void init(ScrollPane pane) {
        scrollPane = pane;
    }

    public void requestUpdate(Student student) {
        rectangleBox.getChildren().clear();

        // Model
        try {
            // Model
            Optional<List<GivenTask>> optional = Optional.ofNullable(Web.getGivenTaskList(student));
            List<GivenTask> givenTaskList = optional.isEmpty() ? Collections.emptyList() : optional.get();

            roadMapModel.update(givenTaskList);
            System.out.println(givenTaskList);

            // Controller
            rectangleTaskList = new ArrayList<>();
            for (GivenTask task : roadMapModel.getGivenTaskList())
                rectangleTaskList.add(new Pair<>(task, createTaskRectangle(task)));

            // View
            rectangleTaskList.forEach(this::drawTaskRectangle);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onScroll(ScrollEvent scrollEvent) {
        /*if (scrollEvent.isControlDown()) {
            zoom(scrollEvent.getDeltaY());
        }
        System.out.println("onScroll");*/

    }

//        //if (taskList != null) {
//
//            /* --------------------- *
//             * Для RoadMapController *
//             * --------------------- */
//
//
//            /* --------------- *
//             * Для RoadMapView *
//             * --------------- */
//            for (Pair<GivenTask, Button> circleTask : circleTaskList)
//                drawTaskCircle(circleTask);
    //}
}
