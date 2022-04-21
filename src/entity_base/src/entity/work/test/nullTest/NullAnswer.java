package  entity.work.test.nullTest;
import entity.work.test.base.Answer;
import entity.work.test.base.Question;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class NullAnswer extends entity.Entity {

    @Getter
    @OneToOne
    private Question givenQuestion;

    @OneToMany
    private List<Answer> givenAnswer;
}
