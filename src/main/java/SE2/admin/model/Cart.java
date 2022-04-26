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


    @OneToOne
    @JoinColumn(name="order_id")
    private Order order;


    @Column(name="total_price")
    private Integer totalPrice;

    @Column(name="status")
    private Integer status;


    @OneToMany(mappedBy = "cart")
    private List<EntityProduct> entityProducts;

    public Cart(String userEmail, Order order, Integer totalPrice, Integer status, List<EntityProduct> entityProducts) {
        this.userEmail = userEmail;
        this.order = order;
        this.totalPrice = totalPrice;
        this.status = status;
        this.entityProducts = entityProducts;
    }
}
