package com.example.astrotalk.controller;

import com.example.astrotalk.dto.HoroscopeDto;
import com.example.astrotalk.service.HoroscopeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/horoscope")
@RequiredArgsConstructor
public class HoroscopeController {
    private final HoroscopeService horoscopeService;

    @PostMapping(
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<String> createHoroscope(@RequestPart(value = "horoscope") HoroscopeDto horoscopeDto, @RequestPart(value = "file") MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        horoscopeService.createHoroscope(horoscopeDto, file)
                );
    }

    @GetMapping(path = "/{hId}")
    public ResponseEntity<HoroscopeDto> findById(@PathVariable long hId) {
        return ResponseEntity
                .ok(
                        horoscopeService.findHoroscopeById(hId)
                );
    }

    @GetMapping
    public ResponseEntity<List<HoroscopeDto>> findAll() {
        return ResponseEntity
                .ok(
                        horoscopeService.findAll()
                );
    }

    @PutMapping(path = "/{hId}")
    public ResponseEntity<String> updateHoroscope(@PathVariable long hId, @RequestBody HoroscopeDto horoscopeDto) {
        return ResponseEntity
                .ok(
                        horoscopeService.updateHoroscope(hId, horoscopeDto)
                );
    }


    @DeleteMapping(path = "/{hId}")
    public ResponseEntity<String> deleteHoroscope(@PathVariable long hId) {
        return ResponseEntity
                .ok(
                  horoscopeService.deleteById(hId)
                );
    }

}
