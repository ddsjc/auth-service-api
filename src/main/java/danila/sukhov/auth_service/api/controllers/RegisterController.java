package danila.sukhov.auth_service.api.controllers;

import danila.sukhov.auth_service.api.dtos.JWTResponceDTO;
import danila.sukhov.auth_service.api.dtos.LoginDTO;
import danila.sukhov.auth_service.api.dtos.MessageResponse;
import danila.sukhov.auth_service.api.dtos.RegistrationDTO;
import danila.sukhov.auth_service.api.services.UserServiceImpl;
import danila.sukhov.auth_service.api.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterController {

    UserService userService;

    UserServiceImpl userServiceImpl;
    public static final String LOG_IN = "api/auth/log-in";
    public static final String SIGN_IN = "api/auth/sign-in";


    @PostMapping(LOG_IN)
    public ResponseEntity<JWTResponceDTO> loginUser (@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(userService.loginUser(loginDTO), HttpStatus.OK);
    }

    @PostMapping(SIGN_IN)
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegistrationDTO registrationDto) {
        return new ResponseEntity<>(userService.registerUser(registrationDto), HttpStatus.OK);
    }

}
