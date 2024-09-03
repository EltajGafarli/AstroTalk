package com.example.astrotalk.controller;

import com.example.astrotalk.dto.HoroscopeDetailsDto;
import com.example.astrotalk.service.HoroscopeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/horoscope-details")
@RequiredArgsConstructor
public class HoroscopeDetailsController {
    private final HoroscopeDetailsService horoscopeDetailsService;

    @PostMapping(path = "/{hId}")
    public ResponseEntity<String> create(@PathVariable long hId, @RequestBody HoroscopeDetailsDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(horoscopeDetailsService.create(hId, dto));
    }

    @GetMapping(path = "/{hId}")
    public ResponseEntity<List<HoroscopeDetailsDto>> getDetails(@PathVariable long hId) {
        return ResponseEntity
                .ok(horoscopeDetailsService.findByHoroscopeId(hId));
    }

    @GetMapping(path = "/{hId}/daily")
    public ResponseEntity<List<HoroscopeDetailsDto>> getDailyHoroscope(@PathVariable long hId) {
        return ResponseEntity
                .ok(horoscopeDetailsService.findDailyHoroscope(hId));
    }

    @GetMapping(path = "/{hId}/weekly")
    public ResponseEntity<List<HoroscopeDetailsDto>> getWeeklyHoroscope(@PathVariable long hId) {
        return ResponseEntity
                .ok(horoscopeDetailsService.findWeeklyHoroscope(hId));
    }

    @GetMapping(path = "/{hId}/monthly")
    public ResponseEntity<List<HoroscopeDetailsDto>> getMonthlyHoroscope(@PathVariable long hId) {
        return ResponseEntity
                .ok(horoscopeDetailsService.findMonthlyHoroscope(hId));
    }
}
