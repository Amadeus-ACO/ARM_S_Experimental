package client.mainWindow.roadMap;

import entity.Section;
import entity.work.GivenTask;
import entity.work.task.Task;
import entity.work.task.TaskVariant;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class RoadMapModel {
    /**
     * Аттрибуты
     */
    private List<GivenTask> givenTaskList;

    public void update(List<GivenTask> givenTaskList) {
        this.givenTaskList = new ArrayList<>();

        Section section = new Section();
        Random random = new Random();
        section.setName("АОВТ");

        for (int i = 1; i <= 10; i++) {
            Task task = new Task();
            task.setName("Выполнение арифметических операций в IEEE 754");
            task.setNumber((random.nextInt(5) + 1) + "." + (random.nextInt(5) + 1));
            task.setSection(section);
            task.setShortDescription("Задание сложное, но интересное");
            //task.setType(i % 2 == 0 ? Task_TYPE.LAB : Task_TYPE.PRACTICE);

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

            this.givenTaskList.add(givenTask);
        }
    }


//
//    /**
//     *
//     * @param labList
//     */
//    public RoadMapModel(ArrayList<Lab> labList)
//    {
//        this.labList = labList;
//    }
//
//    /**
//     *
//     * @param labList
//     * @return
//     */
//    public void setLabList(ArrayList labList)
//    {
//        this.labList = labList;
//
//    }
//
//    public ArrayList getLabList()
//    {
//        return this.labList;
//    }
//
//
//    public String toString()
//    {
//        return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
//                "  " + "labList" + "=" + (getLabList() != null ? !getLabList().equals(this)  ? getLabList().toString().replaceAll("  ","    ") : "this" : "null");
//    }
    //private ArrayList<Lab> labList;

}
