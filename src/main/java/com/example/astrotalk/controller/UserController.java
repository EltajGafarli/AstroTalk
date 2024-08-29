package com.example.astrotalk.controller;

import com.example.astrotalk.dto.UserDto;
import com.example.astrotalk.dto.UserRequestDto;
import com.example.astrotalk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.getCurrentUser(userDetails)
                );
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity
                .ok(
                        userService.updateUser(userDetails, userRequestDto)
                );
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.deleteUser(userDetails)
                );
    }
}
