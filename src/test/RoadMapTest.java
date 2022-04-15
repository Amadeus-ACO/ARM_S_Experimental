import entity.Section;
import entity.work.GivenTask;
import entity.work.task.Task;
import entity.work.task.TaskVariant;
import entity.work.task.Task_TYPE;
import javafx.scene.control.Button;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoadMapTest {

//    public static void testRoadMap() {
//        circleBox.getChildren().clear();
//
//        List<GivenTask> taskList = new ArrayList<>();
//        Section section = new Section();
//        Random random = new Random();
//        section.setName("АОВТ");
//
//        /* ---------------- *
//         * Для RoadMapModel *
//         * ---------------- */
//        for (int i = 1; i <= 10; i++) {
//            Task task = new Task();
//            task.setName("Выполнение арифметических операций в IEEE 754");
//            task.setNumber((random.nextInt(5) + 1) + "." + (random.nextInt(5) + 1));
//            task.setSection(section);
//            task.setShortDescription("Задание сложное, но интересное");
//            task.setType(i % 2 == 0 ? Task_TYPE.LAB : Task_TYPE.PRACTICE);
//
//            TaskVariant taskVariant = new TaskVariant();
//            taskVariant.setTask(task);
//
//            GivenTask givenTask = new GivenTask();
//            givenTask.setTaskVariant(taskVariant);
//            givenTask.setCompleteStatus(switch (new Random().nextInt(5)) {
//                case 0 -> GivenTask.COMPLETE_STATUS.COMPLETED;
//                case 1 -> GivenTask.COMPLETE_STATUS.GIVEN;
//                case 2 -> GivenTask.COMPLETE_STATUS.IN_WORK;
//                case 3 -> GivenTask.COMPLETE_STATUS.ON_FIX;
//                default -> GivenTask.COMPLETE_STATUS.WAITING_FOR_CHECK;
//            });
//            givenTask.setEditStatus(switch (new Random().nextInt(3)) {
//                case 0 -> GivenTask.EDIT_STATUS.AVAILABLE;
//                case 1 -> GivenTask.EDIT_STATUS.IN_PROGRESS;
//                default -> GivenTask.EDIT_STATUS.UNAVAILABLE;
//            });
//
//            taskList.add(givenTask);
//        }
//
//        /* --------------------- *
//         * Для RoadMapController *
//         * --------------------- */
//        circleTaskList = new ArrayList<>();
//        for (GivenTask task : taskList)
//            circleTaskList.add(new Pair<>(task, createTaskCircle(task)));
//
//        /* --------------- *
//         * Для RoadMapView *
//         * --------------- */
//        for (Pair<GivenTask, Button> circleTask : circleTaskList)
//            drawTaskCircle(circleTask);
//    }

}
