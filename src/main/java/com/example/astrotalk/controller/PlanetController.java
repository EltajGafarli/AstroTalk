package com.example.astrotalk.controller;

import com.example.astrotalk.dto.PlanetDto;
import com.example.astrotalk.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/planet")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<String> planetCreated(@RequestPart(value = "planetDto") PlanetDto planetDto, @RequestPart(value = "file")MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        planetService.createPlanet(planetDto, file)
                );
    }

    @GetMapping(path = "/{planetId}")
    public ResponseEntity<PlanetDto> getPlanetById(@PathVariable long planetId) {
        return ResponseEntity
                .ok(
                        planetService.getPlanet(planetId)
                );
    }

    @GetMapping
    public ResponseEntity<List<PlanetDto>> getPlanets() {
        return ResponseEntity
                .ok(
                        planetService.getPlanets()
                );
    }

    @PutMapping(path = "/{planetId}")
    public ResponseEntity<String> updatePlanet(@PathVariable long planetId, @RequestPart(value = "planetDto") PlanetDto planetDto, @RequestPart(value = "file") MultipartFile file) {
        return ResponseEntity
                .ok(
                        planetService.updatePlanet(planetId, planetDto, file)
                );
    }

    @DeleteMapping(path = "/{planetId}")
    public ResponseEntity<String> deletePlanet(@PathVariable long planetId) {
        return ResponseEntity
                .ok(
                        planetService.deletePlanet(planetId)
                );
    }
}
