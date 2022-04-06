package SE2.admin.repository;

import SE2.admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    public User findByUserName(String userName);
}
