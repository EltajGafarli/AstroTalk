package com.example.astrotalk.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequestDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String firstName;
    @NotNull
    @NotEmpty
    @NotBlank
    private String lastName;
    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^(\\+994|0)(50|51|55|70|77)\\d{7}$", message = "Invalid Azerbaijani phone number")
    private String phone;
    @NotNull
    @NotEmpty
    @NotBlank
    private String userName;
}
