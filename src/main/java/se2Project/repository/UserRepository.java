package se2Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se2Project.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long>{

}
