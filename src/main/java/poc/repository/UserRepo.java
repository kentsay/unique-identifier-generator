package poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
