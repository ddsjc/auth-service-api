package danila.sukhov.auth_service.store.repositories;

import danila.sukhov.auth_service.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Long> {
    Stream<User> findByLogin(String login);
    User findUserByLogin(String login);
    boolean existsByLogin(String login);
}
