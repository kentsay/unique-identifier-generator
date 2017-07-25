package poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.model.SeqPrefixedUser;

/**
 * Created by kentsay on 25.07.17.
 */
public interface SeqPrefixedUserRepo extends JpaRepository<SeqPrefixedUser, Long> {
}
