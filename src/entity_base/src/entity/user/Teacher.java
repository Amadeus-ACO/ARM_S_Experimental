package entity.user;

import entity.EntityView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Teacher extends User {

    //@JoinColumn(name = "teacher_id")
    @OneToMany(mappedBy = "teacher")
    @JsonView(EntityView.SHORT.class)
    private List<StudentGroup> studentGroupList;
}
