package SE2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SE2.model.Product;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
}
