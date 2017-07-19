package poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.model.PrefixedUser;

public interface PrefixedUserRepo extends JpaRepository<PrefixedUser, Long> {

}
