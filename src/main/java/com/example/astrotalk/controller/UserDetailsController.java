package com.example.astrotalk.controller;

import com.example.astrotalk.dto.UserDetailsDto;
import com.example.astrotalk.dto.UserDetailsResponseDto;
import com.example.astrotalk.service.UserDetailsServiceForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/user-details")
@RequiredArgsConstructor
public class UserDetailsController {
    private final UserDetailsServiceForUser userDetailsServiceForUser;

    @PostMapping(path = "/{userId}", consumes = {
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<String> createUserDetails(@PathVariable long userId, @RequestPart(value = "userDetailsDto") UserDetailsDto userDetailsDto, @RequestPart(value = "file") MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        userDetailsServiceForUser.createUserDetails(userId, userDetailsDto, file)
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

}
