package com.example.astrotalk.controller;

import com.example.astrotalk.dto.UserDetailsDto;
import com.example.astrotalk.dto.UserDetailsResponseDto;
import com.example.astrotalk.service.UserDetailsServiceForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/user-details")
@RequiredArgsConstructor
public class UserDetailsController {
    private final UserDetailsServiceForUser userDetailsServiceForUser;

    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<String> createUserDetails(@AuthenticationPrincipal UserDetails userDetails, @RequestPart(value = "userDetailsDto") UserDetailsDto userDetailsDto, @RequestPart(value = "file") MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        userDetailsServiceForUser.createUserDetails(userDetails, userDetailsDto, file)
                );
    }

    @GetMapping(path = "/photo/{userId}")
    public ResponseEntity<String> serveFile(@PathVariable long userId) {
        return ResponseEntity
                .ok(
                        userDetailsServiceForUser.serveFile(userId)
                );
    }


    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(@PathVariable long userId) {
        return ResponseEntity
                .ok(this.userDetailsServiceForUser.getUserDetails(userId));
    }

    @PutMapping(path = "/")
    public ResponseEntity<String> updateUserDetails(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserDetailsDto dto) {
        return ResponseEntity
                .ok(
                        this.userDetailsServiceForUser.updateUserDetails(userDetails, dto)
                );
    }

}
