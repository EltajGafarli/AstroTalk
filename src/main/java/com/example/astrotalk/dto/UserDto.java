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
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
}
