package danila.sukhov.auth_service.api.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWTResponceDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private List<String> role;

    public JWTResponceDTO(String token, Long id, String username, List<String> role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
