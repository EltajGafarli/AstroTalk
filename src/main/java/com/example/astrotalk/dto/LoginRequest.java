package com.example.astrotalk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    private String userName;

    @NotNull
    @NotEmpty
    @NotBlank
    private String password;

}
