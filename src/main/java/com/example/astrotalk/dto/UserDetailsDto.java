package com.example.astrotalk.dto;

import com.example.astrotalk.entity.user.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserDetailsDto {
    private Gender gender;
    private Date dateOfBirth;
}
