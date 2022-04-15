package  entity.work.test;

import entity.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class Test extends Entity {

    @Getter
    @RequiredArgsConstructor
    public enum STATUS {
        COMPLETED("Выполнен");
        private final String value;
    }

    //private ArrayList<Question> questionList;
    private STATUS status;
}
