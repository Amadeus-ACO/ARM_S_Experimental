package  entity.work.task;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@javax.persistence.Entity
public class TaskVariant extends entity.Entity {

    @ManyToOne
    private Task task;

    @ElementCollection
    List<Double> values;

//    @ManyToOne
//    @ManyToMany(mappedBy = "formulaSet")
//    @JoinColumns({
//            @JoinColumn(name = "task_id", referencedColumnName = "id"),
//            @JoinColumn(name = "formula_id", referencedColumnName = "formulaSet")
//    })
//    private Task task;
//
//    @ManyToMany(mappedBy = "formula_id")
//    private Set<Double> values;

}
