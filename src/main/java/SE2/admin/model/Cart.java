package SE2.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="total_price")
    private Integer totalPrice;

    @Column(name="status")
    private Integer status;

//    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
//    private List<EntityProduct> entityProducts;

    public Cart(String userEmail, Integer totalPrice, Integer status) {
        this.userEmail = userEmail;
//        this.entityProducts = entityProducts;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

//    public List<EntityProduct> getEntityProducts() {
//        return entityProducts;
//    }
//
//    public void setEntityProducts(List<EntityProduct> entityProducts) {
//        this.entityProducts = entityProducts;
//    }
}
