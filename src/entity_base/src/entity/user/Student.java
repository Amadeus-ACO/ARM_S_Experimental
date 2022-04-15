package entity.user;

import entity.EntityView;
import entity.work.GivenTask;
import entity.lecture.GivenLecture;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.*;

@Getter
@Setter
@Entity
public class Student extends User {

    @OneToMany
    @JsonView(EntityView.STUDENT.class)
    private List<GivenLecture> givenLectureList;

    @OneToMany
    @JsonView({EntityView.SHORT.class,
               EntityView.TEACHER_STUDENT_LIST_IN_GROUP.class,
               EntityView.STUDENT.class})
    private List<GivenTask> givenTaskList;

    @ManyToOne
    //@JsonView(entity.EntityView.SHORT.class)
    @JsonView({EntityView.SHORT.class, EntityView.STUDENT.class})
    private StudentGroup studentGroup;
}

