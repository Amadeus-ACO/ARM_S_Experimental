package entity.work;

import entity.Entity;
import  entity.user.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.util.UUID;

@Getter
@Setter
@javax.persistence.Entity
public class Report extends Entity {

    @ManyToOne
    @JsonIgnore
    private Student owner;

    @Transient
    private UUID ownerId;

    @JsonSetter("owner_id")
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

//    @JsonGetter("owner_id")
//    public UUID getOwnerId() {
//        return owner != null ? owner.getId() : null;
//    }

}
