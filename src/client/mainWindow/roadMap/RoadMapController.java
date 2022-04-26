package client.mainWindow.roadMap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import entity.work.GivenTask;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Класс-контроллер дорожной карты
 */
public class RoadMapController extends RoadMapView {

    @FXML
    private HBox circleBox;

    /**
     * Аттрибуты класса
     */
    private RoadMapModel roadMapModel;
    private RoadMapView roadMapView;

    private List<Pair<GivenTask, Button>> circleTaskList;

    public void setRoadMapModel(RoadMapModel roadMapModel) {
        this.roadMapModel = roadMapModel;
    }

    public void setRoadMapView(RoadMapView roadMapView) {
        this.roadMapView = roadMapView;
    }
    /*private void request () {

    }*/

    private Button createTaskCircle(GivenTask task) {
        Button button = new Button();
        button.setGraphic(new Circle());
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

    public void requestUpdate(List<GivenTask> givenTaskList) {
        circleBox.getChildren().clear();

        // Model
        roadMapModel.update(givenTaskList);

        // Controller
        circleTaskList = new ArrayList<>();
            for (GivenTask task : roadMapModel.getGivenTaskList())
                circleTaskList.add(new Pair<>(task, createTaskCircle(task)));

        // View
        circleTaskList.forEach(this::drawTaskCircle);
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
