package com.example.astrotalk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
}
