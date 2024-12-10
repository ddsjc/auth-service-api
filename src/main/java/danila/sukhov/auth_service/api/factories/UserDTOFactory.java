package danila.sukhov.auth_service.api.factories;

import danila.sukhov.auth_service.api.dtos.UserDTO;
import danila.sukhov.auth_service.store.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOFactory {
    public UserDTO createUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRoles().toString())
                .build();
    }
}
