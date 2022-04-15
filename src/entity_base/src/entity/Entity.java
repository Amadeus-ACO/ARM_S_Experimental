package entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.UUID;

@ToString
@JsonTypeInfo (
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "entity_type"
)
@MappedSuperclass
@Getter
@Setter
public abstract class Entity {

    @JsonView(EntityView.INIT.class)
    private String entityType = this.getClass().getSimpleName();

    @JsonView(EntityView.ID.class)
    @Id
    @Column(nullable = false)
    //@JsonView(entity.EntityView.ID.class)
    private UUID id;
}
