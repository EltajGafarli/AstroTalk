package com.example.astrotalk.dto;

import com.example.astrotalk.entity.planet.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HoroscopeDetailsRequestDto {
    @NotNull
    @NotEmpty
    @NotBlank
    private String features;
    @NotNull
    @NotEmpty
    @NotBlank
    private String planet;
    @NotNull
    @NotEmpty
    @NotBlank
    private Type type;
}
