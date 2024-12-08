package danila.sukhov.auth_service.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import danila.sukhov.auth_service.store.entities.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationDTO {

    String name;

    String surname;

    String login;

    String password;

    Instant createdAt = Instant.now();

    String role;

}
