package SE2.admin.repository;

import SE2.admin.model.EntityProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityProductRepository extends JpaRepository<EntityProduct, Long> {

    List<EntityProduct> findAllByCartIdIs(Long cartId);
}
