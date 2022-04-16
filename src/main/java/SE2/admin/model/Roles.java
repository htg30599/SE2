package SE2.admin.model;

import javax.persistence.*;

@Entity
public class Roles {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String name;
    public Integer getId() {
        return id;
    }

    public Roles() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
