package com.example.astrotalk.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MotivationRequestDto {
    private String motivationLetter;
}
