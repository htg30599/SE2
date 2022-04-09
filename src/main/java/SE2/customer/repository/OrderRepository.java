package SE2.customer.repository;

import SE2.customer.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByNameContaining(String name);
}
