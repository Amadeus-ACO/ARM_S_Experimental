package entity.lecture;

import entity.Entity;
import entity.Section;
import entity.user.Methodist;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Getter
@Setter
@MappedSuperclass
@ToString
public abstract class AbstractLecture extends Entity {

    @ManyToOne
    private Section section;

    @OneToOne
    private Methodist creator;
            
    @OneToMany
    private Set<Methodist> editorList;
}

