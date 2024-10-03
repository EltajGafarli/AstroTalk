package com.example.astrotalk.controller;

import com.example.astrotalk.dto.FullUserDto;
import com.example.astrotalk.dto.UserDto;
import com.example.astrotalk.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity
                .ok(adminService.getAllUsers());
    }

    @GetMapping(path = "/users/full")
    public ResponseEntity<List<FullUserDto>> getAllFullUser() {
        return ResponseEntity
                .ok(adminService.getAllFullUsers());
    }

    @GetMapping(path = "/users/full/{id}")
    public ResponseEntity<FullUserDto> getFullUserById(@PathVariable Long id) {
        return ResponseEntity
                .ok(adminService.getUserById(id));
    }


    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return ResponseEntity
                .ok(adminService.deleteUserById(id));
    }

}
