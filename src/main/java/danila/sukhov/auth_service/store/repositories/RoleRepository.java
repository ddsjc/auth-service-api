package danila.sukhov.auth_service.store.repositories;

import danila.sukhov.auth_service.store.entities.ERole;
import danila.sukhov.auth_service.store.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
