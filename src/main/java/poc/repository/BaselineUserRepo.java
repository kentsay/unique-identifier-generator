package poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poc.model.BaselineUser;

public interface BaselineUserRepo extends JpaRepository<BaselineUser, Long> {

}
