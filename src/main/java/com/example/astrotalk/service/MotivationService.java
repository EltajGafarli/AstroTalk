package com.example.astrotalk.service;

import com.example.astrotalk.dto.MotivationDto;
import com.example.astrotalk.dto.MotivationRequestDto;
import com.example.astrotalk.entity.planet.Motivation;
import com.example.astrotalk.exception.NotFoundException;
import com.example.astrotalk.repository.MotivationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotivationService {
    private final MotivationRepository motivationRepository;

    @Transactional
    public String create(MotivationRequestDto dto, MultipartFile file) {
        String fileName = FileService.handleFileUpload(file, "motivation");
        Motivation motivation = Motivation.builder()
                .motivationLetter(dto.getMotivationLetter())
                .motivationImage(fileName)
                .build();
        motivationRepository.save(motivation);

        return "Motivation Created";
    }

    public MotivationDto getMotivationById(long id) {
        Motivation motivation = motivationRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Motivation not found")
                );

        return motivationToDto(motivation);
    }

    public List<MotivationDto> getAllMotivations() {
        return motivationRepository.findAll()
                .stream()
                .map(this::motivationToDto)
                .toList();
    }

    @Transactional
    public String deleteMotivation(long id) {
        Motivation motivation = motivationRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Motivation not found")
                );

        motivationRepository.delete(motivation);
        return "Motivation Deleted";
    }

    public String updateMotivation(long id, MotivationRequestDto dto, MultipartFile file) {
        Motivation motivation = motivationRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Motivation not found")
                );

        if(file != null && !file.isEmpty()) {
            String fileName = FileService.handleFileUpload(file, "motivation");
            motivation.setMotivationImage(fileName);
        }
        if(dto.getMotivationLetter() != null) {
            motivation.setMotivationLetter(dto.getMotivationLetter());
        }

        motivationRepository.save(motivation);
        return "Motivation Updated";
    }

    private MotivationDto motivationToDto(Motivation motivation) {
        return MotivationDto
                .builder()
                .id(motivation.getId())
                .motivationLetter(motivation.getMotivationLetter())
                .image(motivation.getMotivationImage())
                .build();
    }
}
