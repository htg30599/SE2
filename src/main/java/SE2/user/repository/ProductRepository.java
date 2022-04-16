package SE2.user.repository;

import SE2.user.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

//    List<Product> findProductByCategoriesContainsOrNameContainingOrManufacturerContainsOrShortDescContaining
//            (String name, String shortDesc, String collection, String Manufacturer);

//    @Query(value = "SELECT * FROM product " +
//            "WHERE MATCH(name, shortDesc, manufacturer) " +
//            "AGAINST(?1)", nativeQuery = true)
//    List<Product> search(String keyword);


}
