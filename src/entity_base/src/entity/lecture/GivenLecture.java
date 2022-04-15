package entity.lecture;

import  entity.user.Student;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class GivenLecture extends AbstractLecture {

    @ManyToOne
    private Student ownerId;
}
