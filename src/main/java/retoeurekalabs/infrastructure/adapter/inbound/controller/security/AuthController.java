package retoeurekalabs.infrastructure.adapter.inbound.controller.security;

import retoeurekalabs.infrastructure.adapter.inbound.controller.security.dto.JwtResponse;
import retoeurekalabs.infrastructure.adapter.inbound.controller.security.dto.LoginRequest;
import retoeurekalabs.infrastructure.adapter.inbound.controller.security.dto.MessageResponse;
import retoeurekalabs.infrastructure.adapter.inbound.controller.security.dto.SignupRequest;
import retoeurekalabs.infrastructure.adapter.outbound.persistense.security.entity.Role;
import retoeurekalabs.infrastructure.adapter.outbound.persistense.security.entity.RoleType;
import retoeurekalabs.infrastructure.adapter.outbound.persistense.security.entity.User;
import retoeurekalabs.infrastructure.adapter.outbound.persistense.security.repositories.RoleRepository;
import retoeurekalabs.infrastructure.adapter.outbound.persistense.security.repositories.UserRepository;
import retoeurekalabs.infrastructure.adapter.outbound.persistense.security.services.UserDetailsImpl;
import retoeurekalabs.infrastructure.configuration.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Authenticating user: [{}]", loginRequest.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info("Registering a new user email: [{}], name: [{}], lastName: [{}]", signUpRequest.getEmail(),
                signUpRequest.getName(),
                signUpRequest.getLastName());

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            log.info("Email [{}] already exist", signUpRequest.getEmail());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("Email %s already exist", signUpRequest.getEmail())));
        }

        Role userRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: RoleType not found."));

        User user = User.createUser(signUpRequest.getName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                Set.of(userRole)
        );

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
