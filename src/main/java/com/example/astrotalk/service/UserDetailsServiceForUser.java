package com.example.astrotalk.service;

import com.example.astrotalk.controller.UserController;
import com.example.astrotalk.dto.UserDetailsDto;
import com.example.astrotalk.dto.UserDetailsResponseDto;
import com.example.astrotalk.entity.user.User;
import com.example.astrotalk.entity.user.UserDetails;
import com.example.astrotalk.exception.AlreadyExistException;
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
    public String createUserDetails(org.springframework.security.core.userdetails.UserDetails userDetailsInterface, UserDetailsDto userDetailsDto, MultipartFile file) {

        long userId = userRepository.findByUserName(userDetailsInterface.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found")).getId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        if(user.getUserDetails() != null) {
            throw new AlreadyExistException("User details already exists");
        }

        String fileName = FileService.handleFileUpload(file, "profile");

        UserDetails userDetails = this.dtoToUserDetails(userDetailsDto);
        userDetails.setProfilePictureUrl(fileName);
        userDetails.setUser(user);
        userDetails.setInterests(userDetailsDto.getInterests());
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


    @Transactional
    public UserDetailsResponseDto getUserDetails(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );

        if(user.getUserDetails() == null) {
            throw new NotFoundException("user details not found");
        }

        return detailsToDto(user.getUserDetails());
    }

    @Transactional
    public String updateUserDetails(org.springframework.security.core.userdetails.UserDetails userDetailsInterface, UserDetailsDto userDetailsDto) {
        User user = userRepository.findByUserName(userDetailsInterface.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if(user.getUserDetails() == null) {
            throw new NotFoundException("user details not found");
        }

        UserDetails userDetails = user.getUserDetails();

        if(userDetailsDto.getDateOfBirth() != null) {
            userDetails.setDateOfBirth(userDetailsDto.getDateOfBirth());
        }

        if(userDetailsDto.getGender() != null) {
            userDetails.setGender(userDetailsDto.getGender());
        }

        if(userDetailsDto.getInterests() != null) {
            userDetails.setInterests(userDetailsDto.getInterests());
        }

        userDetailsRepository.save(userDetails);

        return "User Details Updated";
    }



    private UserDetails dtoToUserDetails(UserDetailsDto dto) {
        return UserDetails
                .builder()
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .interests(dto.getInterests())
                .build();
    }


    private UserDetailsResponseDto detailsToDto(UserDetails userDetails) {
        return UserDetailsResponseDto
                .builder()
                .id(userDetails.getId())
                .profilePictureUrl(userDetails.getProfilePictureUrl())
                .dateOfBirth(userDetails.getDateOfBirth())
                .gender(userDetails.getGender())
                .interests(userDetails.getInterests())
                .build();
    }

}
