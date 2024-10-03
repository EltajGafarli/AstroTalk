package com.example.astrotalk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FullUserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private UserDto user;
    private UserDetailsResponseDto userDetails;
}
