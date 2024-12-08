package danila.sukhov.auth_service.api.services;

import danila.sukhov.auth_service.api.dtos.*;
import danila.sukhov.auth_service.store.entities.User;

public interface UserService {
    JWTResponceDTO loginUser(LoginDTO loginDto);
    //MessageResponse loginUser(LoginDTO loginDto);
    MessageResponse registerUser(RegistrationDTO registrationDto);
    User findUserByLogin(String login);
    User update(UpdateDTO updateDto, User currentActor);
}
