package SE2.admin.repository;

import SE2.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
<<<<<<< HEAD
//    @Query("SELECT u FROM User u WHERE u.email = ?1")
=======
>>>>>>> 940bdc3fd1dbf4446e3a298ecf340b75001e5972
    User findByEmail(String email);

    List<User> findByUserNameContaining(String name);
}
