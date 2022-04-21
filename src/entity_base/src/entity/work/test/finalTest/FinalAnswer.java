package  entity.work.test.finalTest;

import entity.work.test.base.Question;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class FinalAnswer extends entity.Entity {

    @Getter
    @OneToOne
    private Question givenQuestion;

    @Getter
    private String givenAnswer;
}
