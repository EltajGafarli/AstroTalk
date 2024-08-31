package com.example.astrotalk.service;

import com.example.astrotalk.dto.UserDetailsDto;
import com.example.astrotalk.dto.UserDetailsResponseDto;
import com.example.astrotalk.entity.user.User;
import com.example.astrotalk.entity.user.UserDetails;
import com.example.astrotalk.exception.NotFoundException;
import com.example.astrotalk.repository.UserDetailsRepository;
import com.example.astrotalk.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceForUser {
    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;
    @Transactional
    public String createUserDetails(long userId, UserDetailsDto userDetailsDto, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        String fileName = FileService.handleFileUpload(file, "profile");

        UserDetails userDetails = this.dtoToUserDetails(userDetailsDto);
        userDetails.setProfilePictureUrl(fileName);
        userDetails.setUser(user);
        user.setUserDetails(userDetails);
        userDetailsRepository.save(userDetails);
        return "Details Created Successfully";
    }


    public String serveFile(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
        UserDetails userDetails = user.getUserDetails();
        if(userDetails == null) {
            throw new NotFoundException("User details not found");
        }
        return userDetails.getProfilePictureUrl();
    }


    public UserDetailsResponseDto getUserDetails(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
        return detailsToDto(user.getUserDetails());
    }

    private UserDetails dtoToUserDetails(UserDetailsDto dto) {
        return UserDetails
                .builder()
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .build();
    }


    private UserDetailsResponseDto detailsToDto(UserDetails userDetails) {
        return UserDetailsResponseDto
                .builder()
                .profilePictureUrl(userDetails.getProfilePictureUrl())
                .dateOfBirth(userDetails.getDateOfBirth())
                .gender(userDetails.getGender())
                .build();
    }

}
