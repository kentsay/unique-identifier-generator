package poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.model.SeqPrefixedUser;

public interface SeqPrefixedUserRepo extends JpaRepository<SeqPrefixedUser, Long> {

}
