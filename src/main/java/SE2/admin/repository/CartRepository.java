package SE2.admin.repository;

import SE2.admin.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository  extends JpaRepository<Cart, Long> {
    Cart findByUserEmail(String email);
    Optional<Cart> findById(Long id);
    List<Cart> findAllById(int id);

    Cart findByUserEmailAndStatusIs(String userEmail, Integer status);
}

