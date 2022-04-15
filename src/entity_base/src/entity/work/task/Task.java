package  entity.work.task;

import entity.EntityView;
import entity.Section;
import entity.user.Methodist;
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
public class Task extends entity.Entity {

    //@Column(name = "name", nullable = false)
    @JsonView(EntityView.SHORT.class)
    @Column(unique = true)
    private String name;

    // ????
    private String number;

    @ManyToOne
    private Methodist creator;

    @ManyToMany
    private Set<Methodist> editorList;

   // @Column(name = "type", nullable = false)
    @JsonView(EntityView.SHORT.class)
    private Task_TYPE type;

    @ManyToOne
    private Section section;

    @JsonView(EntityView.SHORT.class)
    private String shortDescription;

    private String text;

    @OneToMany
    private List<Formula> formulaList;

}
