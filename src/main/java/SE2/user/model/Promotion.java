package SE2.user.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String name;

    private String shortDesc;

    @NotEmpty
    private Date beginDate;

    @NotEmpty
    private Date endDate;

    @NotEmpty
    private int discountPer;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(int discountPer) {
        this.discountPer = discountPer;
    }
}
