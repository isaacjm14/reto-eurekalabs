package retoeurekalabs.infrastructure.adapter.outbound.persistense.security.repositories;

import retoeurekalabs.infrastructure.adapter.outbound.persistense.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
    Boolean existsByEmail(String email);
}
