package SE2.admin.repository;

import SE2.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByUserName(String name);
    List<User> findByUserNameContaining(String name);

    User findByEmail(String username);
}
