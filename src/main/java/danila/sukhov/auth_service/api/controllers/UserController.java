package danila.sukhov.auth_service.api.controllers;

import danila.sukhov.auth_service.api.dtos.UpdateDTO;
import danila.sukhov.auth_service.api.services.UserService;
import danila.sukhov.auth_service.store.entities.User;
import danila.sukhov.auth_service.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;

    public static final String GET_USER = "/user/get";
    public static final String UPDATE_USER = "/user/update";

    @GetMapping(GET_USER)
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDetails userDetails){
        User user = userService.findUserByLogin(userDetails.getUsername()); //userRepository
        return ResponseEntity.ok(user);
    }
    @PatchMapping(UPDATE_USER)
    public ResponseEntity<User> updateUser(@RequestBody UpdateDTO actorDto, @AuthenticationPrincipal UserDetails userDetails){
        User currentActor = userService.findUserByLogin(userDetails.getUsername()); //userRepository
        User newActor = userService.update(actorDto, currentActor);
        return ResponseEntity.ok(newActor);
    }
}
