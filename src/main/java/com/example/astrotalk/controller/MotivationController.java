package com.example.astrotalk.controller;

import com.example.astrotalk.dto.MotivationDto;
import com.example.astrotalk.dto.MotivationRequestDto;
import com.example.astrotalk.service.MotivationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/motivation")
@RequiredArgsConstructor
public class MotivationController {
    private final MotivationService motivationService;

    @PostMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<String> create(@RequestPart(value = "motivation") MotivationRequestDto motivationRequestDto, @RequestPart(value = "file") MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        motivationService.create(motivationRequestDto, file)
                );
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<MotivationDto> getById(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        motivationService.getMotivationById(id)
                );
    }

    @GetMapping
    public ResponseEntity<List<MotivationDto>> getAll() {
        return ResponseEntity
                .ok(
                        motivationService.getAllMotivations()
                );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        motivationService.deleteMotivation(id)
                );
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestPart(value = "motivation", required = false) MotivationRequestDto dto, @RequestPart(value = "file", required = false) MultipartFile file) {
        return ResponseEntity
                .ok(
                        motivationService.updateMotivation(id, dto, file)
                );
    }


}
