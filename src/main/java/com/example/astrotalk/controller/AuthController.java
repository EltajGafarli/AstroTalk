package com.example.astrotalk.controller;

import com.example.astrotalk.dto.JwtResponseDto;
import com.example.astrotalk.dto.LoginRequest;
import com.example.astrotalk.dto.RegisterRequest;
import com.example.astrotalk.service.AuthService;
import com.example.astrotalk.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        log.info(request.toString());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        authService.register(request)
                );
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.authentication(loginRequest));
    }

    @PostMapping(path = "/{userName}")
    public ResponseEntity<String> acceptedPrivacy(@PathVariable String userName) {
        this.authService.acceptPrivacyPolicy(userName);
        return ResponseEntity
                .ok("Privacy Policy accepted successfully");

    }

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
        return ResponseEntity
                .ok("token refreshed");
    }
}
