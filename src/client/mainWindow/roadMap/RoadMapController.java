package client.mainWindow.roadMap;

import entity.Section;
import entity.user.Student;
import entity.work.task.Task;
import entity.work.task.TaskVariant;
import entity.work.task.Task_TYPE;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import entity.work.GivenTask;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Класс-контроллер дорожной карты
 */
public class RoadMapController extends RoadMapView implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void updateModel(Student student) {
        circleBox.getChildren().clear();

        //System.out.println(student.getGivenTaskList());
       //List<GivenTask> taskList = student.getGivenTaskList();
        List<GivenTask> taskList = new ArrayList<>();
        Section section = new Section();
        Random random = new Random();
        section.setName("АОВТ");
        /* ---------------- *
         * Для RoadMapModel *
         * ---------------- */
        for (int i = 1; i <= 10; i++) {
            Task task = new Task();
            task.setName("Выполнение арифметических операций в IEEE 754");
            task.setNumber((random.nextInt(5) + 1) + "." + (random.nextInt(5) + 1));
            task.setSection(section);
            task.setShortDescription("Задание сложное, но интересное");
            task.setType(i % 2 == 0 ? Task_TYPE.LAB : Task_TYPE.PRACTICE);

            TaskVariant taskVariant = new TaskVariant();
            taskVariant.setTask(task);

            GivenTask givenTask = new GivenTask();
            givenTask.setTaskVariant(taskVariant);
            givenTask.setCompleteStatus(switch (new Random().nextInt(5)) {
                case 0 -> GivenTask.COMPLETE_STATUS.COMPLETED;
                case 1 -> GivenTask.COMPLETE_STATUS.GIVEN;
                case 2 -> GivenTask.COMPLETE_STATUS.IN_WORK;
                case 3 -> GivenTask.COMPLETE_STATUS.ON_FIX;
                default -> GivenTask.COMPLETE_STATUS.WAITING_FOR_CHECK;
            });
            givenTask.setEditStatus(switch (new Random().nextInt(3)) {
                case 0 -> GivenTask.EDIT_STATUS.AVAILABLE;
                case 1 -> GivenTask.EDIT_STATUS.IN_PROGRESS;
                default -> GivenTask.EDIT_STATUS.UNAVAILABLE;
            });

            taskList.add(givenTask);
        }

        //if (taskList != null) {

            /* --------------------- *
             * Для RoadMapController *
             * --------------------- */
            circleTaskList = new ArrayList<>();
            for (GivenTask task : taskList)
                circleTaskList.add(new Pair<>(task, createTaskCircle(task)));

            /* --------------- *
             * Для RoadMapView *
             * --------------- */
            for (Pair<GivenTask, Button> circleTask : circleTaskList)
                drawTaskCircle(circleTask);
        //}
    }
}
