package SE2.admin.repository;

import SE2.admin.model.Category;
import SE2.admin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.OptionPaneUI;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    List<Product> findByCategory(Category category);

    Optional<Product> findById(String id);
}
