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

import java.util.List;

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

    @PostMapping("/follow/{userToFollowId}")
    public ResponseEntity<String> followUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long userToFollowId) {
        userService.followUser(userDetails, userToFollowId);
        return ResponseEntity.ok("Successfully followed the user.");
    }

    @PostMapping("/unfollow/{userToUnfollowId}")
    public ResponseEntity<String> unfollowUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long userToUnfollowId) {
        userService.unfollowUser(userDetails, userToUnfollowId);
        return ResponseEntity.ok("Successfully unfollowed the user.");
    }

    @GetMapping("/followersCount")
    public ResponseEntity<Integer> getFollowersCount(@AuthenticationPrincipal UserDetails userDetails) {
        int followersCount = userService.getFollowersCount(userDetails);
        return ResponseEntity.ok(followersCount);
    }

    @GetMapping("/followingCount")
    public ResponseEntity<Integer> getFollowingCount(@AuthenticationPrincipal UserDetails userDetails) {
        int followingCount = userService.getFollowingCount(userDetails);
        return ResponseEntity.ok(followingCount);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<UserDto>> recommendUsersToFollow(@AuthenticationPrincipal UserDetails userDetails) {
        List<UserDto> recommendedUsers = userService.recommendUsersToFollow(userDetails);
        return ResponseEntity.ok(recommendedUsers);
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username) {
        return ResponseEntity
                .ok(userService.findByUserName(username));
    }
}
