package SE2.admin.repository;

import SE2.admin.model.Cart;
import SE2.admin.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository  extends JpaRepository<Cart, Long> {
    Cart findByUserEmail(String email);
}
