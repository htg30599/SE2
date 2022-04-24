package SE2.admin.repository;

import SE2.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
<<<<<<< HEAD
public interface UserRepository extends JpaRepository<User, Long>{
=======
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
>>>>>>> 7d540d7eb6b2ce1f1993c6ce4390872a1c3318bc

    List<User> findByUserNameContaining(String name);

    User findByEmail(String username);
}
