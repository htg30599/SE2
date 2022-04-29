package SE2.admin.repository;

import SE2.admin.model.Cart;
import SE2.admin.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);
    List<Order> findAllById(int id);

    Order findByCart(Cart cart);
}
