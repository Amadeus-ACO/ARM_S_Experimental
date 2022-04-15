package  entity.work.test.finalTest;

import  entity.work.test.Test;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class FinalTest extends Test {

    @OneToMany
    private List<FinalAnswer> finalAnswerArrayList;
}
