package entity.lecture;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
public class Lecture extends AbstractLecture implements Serializable {

}
