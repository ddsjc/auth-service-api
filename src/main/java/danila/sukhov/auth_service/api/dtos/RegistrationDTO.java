package danila.sukhov.auth_service.api.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.Instant;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationDTO {

    String name;

    String surname;

    @NotBlank(message = "Login cannot be blank")
    @Size(max = 50, message = "Login must not exceed 50 characters")
    String login;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 255, message = "Password must not exceed 255 characters")
    String password;

    Instant createdAt = Instant.now();

    String role;

}
