package com.example.astrotalk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HoroscopeDto {
    private long id;
    private String horoscopeName;
    private String image;
}
