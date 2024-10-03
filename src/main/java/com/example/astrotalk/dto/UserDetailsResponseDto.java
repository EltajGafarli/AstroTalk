package com.example.astrotalk.dto;

import com.example.astrotalk.entity.user.Gender;
import com.example.astrotalk.entity.user.Interests;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class UserDetailsResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String profilePictureUrl;
    private Gender gender;
    private Date dateOfBirth;
    private List<Interests> interests;
}
