package com.example.astrotalk.service;

import com.example.astrotalk.dto.FullUserDto;
import com.example.astrotalk.dto.UserDetailsResponseDto;
import com.example.astrotalk.dto.UserDto;
import com.example.astrotalk.entity.user.User;
import com.example.astrotalk.entity.user.UserDetails;
import com.example.astrotalk.exception.NotFoundException;
import com.example.astrotalk.repository.UserRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::userToDto)
                .toList();
    }

    @Transactional
    public List<FullUserDto> getAllFullUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::userToFullDto)
                .toList();
    }


    public FullUserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return userToFullDto(user);
    }

    @Transactional
    public String deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
        return "User deleted";
    }

    private UserDto userToDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUsername())
                .build();
    }

    private UserDetailsResponseDto detailsResponseDto(UserDetails userDetails) {
        if(userDetails == null) {
            return null;
        }
        return UserDetailsResponseDto
                .builder()
                .id(userDetails.getId())
                .dateOfBirth(userDetails.getDateOfBirth())
                .gender(userDetails.getGender())
                .interests(userDetails.getInterests())
                .profilePictureUrl(userDetails.getProfilePictureUrl())
                .build();
    }

    private FullUserDto userToFullDto(User user) {
        return FullUserDto
                .builder()
                .user(userToDto(user))
                .userDetails(detailsResponseDto(user.getUserDetails()))
                .build();
    }
}
