package  entity.work.task;

import entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@javax.persistence.Entity
public class Formula extends Entity {
    private String name;
    private String propertyLaTeX;

//    @Transient
//    @OneToMany
//    private Collection<Double> inputValues;
}
