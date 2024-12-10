package danila.sukhov.auth_service.api.services;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import danila.sukhov.auth_service.api.dtos.*;
import danila.sukhov.auth_service.api.exception.GlobalException;
import danila.sukhov.auth_service.api.factories.UserDTOFactory;
import danila.sukhov.auth_service.configs.jwt.JWTUtils;
import danila.sukhov.auth_service.store.entities.ERole;
import danila.sukhov.auth_service.store.entities.Role;
import danila.sukhov.auth_service.store.entities.User;
import danila.sukhov.auth_service.store.repositories.RoleRepository;
import danila.sukhov.auth_service.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JWTUtils jwtProvider;

    AuthenticationManager authenticationManager;
    public MessageResponse registerUser(RegistrationDTO registrationDTO){
        if(userRepository.existsByLogin(registrationDTO.getLogin())){
            throw new RuntimeException("User already exists!");
        }

        String encodePassword = passwordEncoder.encode(registrationDTO.getPassword());

        User user = User.builder()
                .name(registrationDTO.getName())
                .surname(registrationDTO.getSurname())
                .login(registrationDTO.getLogin())
                .password(encodePassword)
                .build();

        String requestRole = registrationDTO.getRole();
        Set<Role> roles = new HashSet<>();

        switch (requestRole) {
            case "ADMIN":
                Role roleAdmin = roleRepository.findByName(ERole.ADMIN)
                        .orElseThrow(() -> new GlobalException("Роль 'Админ' не найдена", HttpStatus.BAD_REQUEST));
                roles.add(roleAdmin);
                break;
            case "EXECUTOR":
                Role roleExecutor = roleRepository.findByName(ERole.EXECUTOR)
                        .orElseThrow(() -> new GlobalException("Роль 'Исполнитель' не найдена", HttpStatus.BAD_REQUEST));
                roles.add(roleExecutor);
                break;
            default:
                throw new GlobalException("Указана недопустимая роль", HttpStatus.BAD_REQUEST);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("Пользователь : " + user.getLogin() + " успешно зарегистрирован");
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }
    //
    @Override
    public User update(UpdateDTO updateDto, User currentUser) {

        if((!currentUser.getLogin().equals(updateDto.getLogin()) && userRepository.existsByLogin(currentUser.getLogin()))){
            throw new GlobalException(String.format("User with login:  \"%s\" already exists!", currentUser.getLogin()), HttpStatus.BAD_REQUEST);
        }
        currentUser.setId(currentUser.getId());
        currentUser.setName(updateDto.getName().isBlank() ? currentUser.getName() : updateDto.getName());
        currentUser.setSurname(updateDto.getSurname().isBlank() ? currentUser.getSurname() : updateDto.getSurname());
        currentUser.setLogin(updateDto.getLogin().isBlank() ? currentUser.getLogin() : updateDto.getLogin());
        currentUser.setPassword(updateDto.getPassword().isBlank() ? currentUser.getPassword() : passwordEncoder.encode(updateDto.getPassword()));

        userRepository.saveAndFlush(currentUser);
        return currentUser;
    }



    public JWTResponceDTO loginUser(LoginDTO loginDTO){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJWTToken(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JWTResponceDTO(jwt, userDetails.getId(), userDetails.getUsername(), roles);
    }
}
