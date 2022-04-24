package SE2.admin.repository;

import SE2.admin.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findAllById(int id);
}
<<<<<<< HEAD
=======

>>>>>>> 7d540d7eb6b2ce1f1993c6ce4390872a1c3318bc
