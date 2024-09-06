package com.example.astrotalk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanetDto {

    private long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    private String image;
    @NotNull
    @NotBlank
    @NotEmpty
    private String gravitation;
    @NotNull
    @NotBlank
    @NotEmpty
    private String area;
    @NotNull
    @NotBlank
    @NotEmpty
    private String about;
}
