package SE2.admin.repository;

import SE2.admin.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findAllById(int id);
<<<<<<< HEAD
}
=======
}
>>>>>>> 940bdc3fd1dbf4446e3a298ecf340b75001e5972
