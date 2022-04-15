package entity;

import javax.persistence.Entity;
import java.sql.Date;

@Entity
public class News extends entity.Entity {

    private Date publishDate;
    private String innerText;

}
