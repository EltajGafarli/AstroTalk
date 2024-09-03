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

    @PostMapping("/{userId}/follow/{userToFollowId}")
    public ResponseEntity<String> followUser(@PathVariable Long userId, @PathVariable Long userToFollowId) {
        userService.followUser(userId, userToFollowId);
        return ResponseEntity.ok("Successfully followed the user.");
    }

    @PostMapping("/{userId}/unfollow/{userToUnfollowId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long userId, @PathVariable Long userToUnfollowId) {
        userService.unfollowUser(userId, userToUnfollowId);
        return ResponseEntity.ok("Successfully unfollowed the user.");
    }

    @GetMapping("/{userId}/followersCount")
    public ResponseEntity<Integer> getFollowersCount(@PathVariable Long userId) {
        int followersCount = userService.getFollowersCount(userId);
        return ResponseEntity.ok(followersCount);
    }

    @GetMapping("/{userId}/followingCount")
    public ResponseEntity<Integer> getFollowingCount(@PathVariable Long userId) {
        int followingCount = userService.getFollowingCount(userId);
        return ResponseEntity.ok(followingCount);
    }

    @GetMapping("/{userId}/recommendations")
    public ResponseEntity<List<UserDto>> recommendUsersToFollow(@PathVariable Long userId) {
        List<UserDto> recommendedUsers = userService.recommendUsersToFollow(userId);
        return ResponseEntity.ok(recommendedUsers);
    }
}
