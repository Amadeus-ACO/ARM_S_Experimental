package  entity.work.solution;

import entity.Entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;

@Getter
@Setter
@javax.persistence.Entity
public class Solution extends Entity {

    @OneToOne
    private Algorithm algorithm;

    //private String conclusion;
}
