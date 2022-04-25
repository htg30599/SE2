package SE2.admin.repository;

import SE2.admin.model.Category;
import SE2.admin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    List<Product> findByCategory(Category category);
}
