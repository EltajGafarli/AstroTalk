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

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void followUser(UserDetails userDetails, Long userToFollowId) {
        long userId = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found")).getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User userToFollow = userRepository.findById(userToFollowId).orElseThrow(() -> new RuntimeException("User to follow not found"));



        user.followUser(userToFollow);
        userRepository.save(user);
        userRepository.save(userToFollow);
    }

    @Transactional
    public void unfollowUser(UserDetails userDetails, Long userToUnfollowId) {
        long userId = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found")).getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User userToUnfollow = userRepository.findById(userToUnfollowId).orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        user.unfollowUser(userToUnfollow);
        userRepository.save(user);
        userRepository.save(userToUnfollow);
    }

    @Transactional
    public int getFollowersCount(UserDetails userDetails) {
        long userId = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found")).getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowers().size();
    }

    @Transactional
    public int getFollowingCount(UserDetails userDetails) {
        long userId = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found")).getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowing().size();
    }

    @Transactional
    public List<UserDto> recommendUsersToFollow(UserDetails userDetails) {
        long userId = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found")).getId();
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<User> allUsers = userRepository.findAll();
        allUsers.remove(currentUser);

        List<User> usersNotFollowed = allUsers.stream()
                .filter(user -> currentUser.getFollowing().stream().noneMatch(follower -> follower.getUser().equals(user)))
                .toList();

        return usersNotFollowed.stream()
                .sorted((user1, user2) -> {
                    long sharedInterestsUser1 = user1.getUserDetails() != null ? user1.getUserDetails().getInterests().stream()
                            .filter(interest -> currentUser.getUserDetails().getInterests().contains(interest))
                            .count() : 0;

                    long sharedInterestsUser2 = user2.getUserDetails() != null ? user2.getUserDetails().getInterests().stream()
                            .filter(interest -> currentUser.getUserDetails().getInterests().contains(interest))
                            .count() : 0;

                    return Long.compare(sharedInterestsUser2, sharedInterestsUser1);
                })
                .limit(10)
                .map(this::userToUserDto)
                .toList();

    }

    public UserDto findByUserName(String userName) {
        return userToUserDto(userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    private UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userName(user.getUsername())
                .build();
    }
}
