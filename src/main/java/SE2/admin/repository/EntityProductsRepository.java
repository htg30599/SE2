package SE2.admin.repository;

import SE2.admin.model.Cart;
import SE2.admin.model.EntityProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityProductsRepository extends JpaRepository<EntityProduct, Long> {
    List<EntityProduct> findAllByCart(Cart cart);

    List<EntityProduct> findAllByIdIn(List<Long> ids);
}
