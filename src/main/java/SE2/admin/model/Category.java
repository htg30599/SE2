package SE2.admin.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    private Long id;
    @Column(name="name")
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @NotEmpty(message = "*Name cannot be empty!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}