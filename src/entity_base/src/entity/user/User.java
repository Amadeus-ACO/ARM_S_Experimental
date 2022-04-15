package entity.user;

import entity.Entity;
import entity.EntityView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class User extends Entity {

    @JsonView({EntityView.SHORT.class, EntityView.STUDENT.class})
    private ROLE role;

    @Column(nullable = false)
    @JsonView({EntityView.SHORT.class, EntityView.STUDENT.class})
    private String name;

}
