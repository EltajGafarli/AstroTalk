package com.example.astrotalk.dto;

import com.example.astrotalk.entity.user.Gender;
import com.example.astrotalk.entity.user.Interests;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class UserDetailsResponseDto {
    private long id;
    private String profilePictureUrl;
    private Gender gender;
    private Date dateOfBirth;
    private List<Interests> interests;
}
