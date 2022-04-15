package entity.work;
import entity.Entity;
import entity.work.solution.Solution;
import entity.work.task.TaskVariant;
import entity.work.test.finalTest.FinalTest;
import entity.work.test.nullTest.NullTest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.sql.Date;

@Getter
@Setter
@javax.persistence.Entity
public class GivenTask extends Entity {

    @Getter
    @RequiredArgsConstructor
    public enum COMPLETE_STATUS {
        GIVEN("Выдана"),                        // Работа выдана (студент получил лабораторную работу)
        IN_WORK("В работе"),                    // Работа выполняется (студент открыл лабораторную работу)
        WAITING_FOR_CHECK("Ожидает проверки"),  // Работа ожидает проверки (студент отправил лабораторную работу на проверку)
        ON_FIX("На исправлении"),               // Работа на исправлении (в отправленной работе были допущены ошибки)
        COMPLETED("Выполнена");                 // Работа выполнена (преподаватель проверил и оценил работу)
        private final String value;
    }

    @Getter
    @RequiredArgsConstructor
    public enum EDIT_STATUS {
        AVAILABLE("Доступна"),                  // Доступна
        IN_PROGRESS("В процессе выполнения"),   // В процессе выполнения (в реальном времени)
        UNAVAILABLE("Не доступна");             // Не доступна
        private final String value;
    }

    // DEFAULT ENTITIES

    private Date startDate;
    private Date finishDate;
    private COMPLETE_STATUS completeStatus;
    private EDIT_STATUS editStatus;

    // ATTACHED ENTITIES

    @OneToOne
    private TaskVariant taskVariant;

    @OneToOne
    private NullTest nullTest;

    @OneToOne
    private Solution solution;

    @OneToOne
    private FinalTest finalTest;

    @OneToOne
    private Report report;

}
