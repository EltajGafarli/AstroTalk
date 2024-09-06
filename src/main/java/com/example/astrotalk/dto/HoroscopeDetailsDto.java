package com.example.astrotalk.dto;

import lombok.Builder;
import lombok.Data;
import com.example.astrotalk.entity.planet.Type;

@Data
@Builder
public class HoroscopeDetailsDto {
    private long id;
    private String features;
    private String planet;
    private Type type;
}
