package com.example.astrotalk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MotivationDto {
    private long id;
    private String motivationLetter;
    private String image;
}
