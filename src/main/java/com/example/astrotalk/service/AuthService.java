package com.example.astrotalk.service;

import com.example.astrotalk.dto.JwtResponseDto;
import com.example.astrotalk.dto.LoginRequest;
import com.example.astrotalk.dto.RegisterRequest;
import com.example.astrotalk.entity.user.Role;
import com.example.astrotalk.entity.user.RoleEnum;
import com.example.astrotalk.entity.user.User;
import com.example.astrotalk.exception.AlreadyExistException;
import com.example.astrotalk.exception.NotFoundException;
import com.example.astrotalk.repository.RoleRepository;
import com.example.astrotalk.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Value(value = "${admin.email}")
    private String ADMIN_EMAIL;

    @Transactional(rollbackOn = Exception.class)
    public String register(RegisterRequest registerRequest) {
        userRepository.findByUserName(registerRequest.getUserName())
                .ifPresent((data) -> {
                    throw new AlreadyExistException("User already exist!!!");
                });

        User user = getUser(registerRequest);

        user.addRole(
                Role
                        .builder()
                        .roleEnum(RoleEnum.USER)
                        .build()
        );

        if(ADMIN_EMAIL.equals(registerRequest.getEmail())) {
            user.addRole(
                    Role
                            .builder()
                            .roleEnum(RoleEnum.ADMIN)
                            .build()
            );
        }

        userRepository.save(user);
        return "Register";
    }

    public JwtResponseDto authentication(LoginRequest loginRequest) {

        userRepository.findByUserName(loginRequest.getUserName()).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        String userName = loginRequest.getUserName();
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> {throw new NotFoundException("User not found");}
        );


        String jwt = jwtService.generateToken(user);
        return getToken(jwt);

    }

    @Transactional(rollbackOn = Exception.class)
    public void acceptPrivacyPolicy(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("user not found"));
        user.setPrivacyPolicyAccepted(true);
        userRepository.save(user);
    }

    private User getUser(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .userName(registerRequest.getUserName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .isEnabled(true)
                .privacyPolicyAccepted(false)
                .build();

    }

    private JwtResponseDto getToken(String jwt) {
        return JwtResponseDto.builder()
                .accessToken(jwt)
                .build();
    }
}
