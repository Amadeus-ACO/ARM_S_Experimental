package entity.user;

import entity.Entity;
import entity.EntityView;
import  entity.work.task.Task;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@javax.persistence.Entity
public class StudentGroup extends Entity {

    @JsonView(EntityView.SHORT.class)
    @OneToMany(mappedBy = "studentGroup")
    private Set<Student> inGroupStudent;

    @JsonView({ EntityView.SHORT.class,
                EntityView.TEACHER_GROUP_LIST.class,
                EntityView.STUDENT.class})
    private String name;

    @ManyToOne
    // @JoinColumn(name="teacher_id", nullable=false)
    @JsonView({EntityView.STUDENT.class})
    private Teacher teacher;

    @ManyToMany
    @JsonView({ EntityView.SHORT.class})
    private List<Task> taskList;
}
