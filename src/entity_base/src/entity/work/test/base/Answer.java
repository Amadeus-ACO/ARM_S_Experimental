package entity.work.test.base;

import com.fasterxml.jackson.annotation.JsonView;
import entity.EntityView;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
public class Answer extends entity.Entity {

    @Getter
    private String text;

    @Getter
    @JsonView(EntityView.METHODIST_QUESTION.class)
    private boolean rightFlag;
}
