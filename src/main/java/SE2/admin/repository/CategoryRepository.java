package SE2.admin.repository;

import SE2.admin.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
