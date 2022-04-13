package SE2.admin.model;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity

public class Carousel {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 @NotNull
 @Column(columnDefinition = "MEDIUMBLOB")
 private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}