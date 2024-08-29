package com.example.astrotalk.service;

import com.example.astrotalk.dto.UserDto;
import com.example.astrotalk.dto.UserRequestDto;
import com.example.astrotalk.entity.user.User;
import com.example.astrotalk.exception.NotFoundException;
import com.example.astrotalk.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getCurrentUser(UserDetails userDetails) {
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userToUserDto(user);
    }

    @Transactional(rollbackOn = Exception.class)
    public UserDto updateUser(UserDetails userDetails, UserRequestDto userRequestDto) {
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setFirstName(userRequestDto.getFirstName());
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setLastName(userRequestDto.getLastName());
        user.setPhone(userRequestDto.getPhone());
        User savedUser = userRepository.save(user);
        return userToUserDto(savedUser);
    }


    @Transactional(rollbackOn = Exception.class)
    public String deleteUser(UserDetails userDetails) {
        User user = userRepository.findByUserName(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        userRepository.delete(user);
        return "User deleted successfully";
    }

    private UserDto userToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .userName(user.getUsername())
                .build();
    }
}
