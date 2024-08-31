package com.example.astrotalk.service;

import com.example.astrotalk.dto.PlanetDto;
import com.example.astrotalk.entity.planet.Planet;
import com.example.astrotalk.exception.NotFoundException;
import com.example.astrotalk.repository.PlanetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanetService {
    private final PlanetRepository planetRepository;

    @Transactional
    public String createPlanet(PlanetDto planetDto, MultipartFile file) {
        String fileName = FileService.handleFileUpload(file, "planet");
        planetDto.setImage(fileName);
        Planet planet = dtoToPlanet(planetDto);
        planetRepository.save(planet);
        return "Planet created successfully";
    }

    public String updatePlanet(long planetId, PlanetDto planetDto, MultipartFile file) {
        Planet planet = planetRepository.findById(planetId)
                .orElseThrow(
                        () -> new NotFoundException("Planet not found")
                );
        String fileName = FileService.handleFileUpload(file, "planet");
        planetDto.setImage(fileName);
        if(planetDto.getAbout() != null) {
            planet.setAbout(planetDto.getAbout());
        }

        if(planetDto.getArea() != null) {
            planet.setArea(planetDto.getArea());
        }

        if(planetDto.getGravitation() != null) {
            planet.setGravitation(planetDto.getGravitation());
        }

        if(planetDto.getImage() != null) {
            planet.setImage(planetDto.getImage());
        }

        if(planetDto.getName() != null) {
            planet.setName(planetDto.getName());
        }

        planetRepository.save(planet);

        return "Update planet";
    }

    public PlanetDto getPlanet(long planetId) {
        Planet planet = planetRepository.findById(planetId)
                .orElseThrow(
                        () -> new NotFoundException("Planet not found")
                );
        return this.planetToDto(planet);
    }

    public List<PlanetDto> getPlanets() {
        return planetRepository
                .findAll()
                .stream().map(this::planetToDto)
                .toList();
    }

    @Transactional
    public String deletePlanet(long planetId) {
        planetRepository.deleteById(planetId);
        return "Planet Deleted successfully";
    }

    private Planet dtoToPlanet(PlanetDto planetDto) {
        return Planet
                .builder()
                .name(planetDto.getName())
                .about(planetDto.getAbout())
                .area(planetDto.getArea())
                .gravitation(planetDto.getGravitation())
                .image(planetDto.getImage())
                .build();
    }

    private PlanetDto planetToDto(Planet planet) {
        return PlanetDto
                .builder()
                .name(planet.getName())
                .about(planet.getAbout())
                .area(planet.getArea())
                .gravitation(planet.getGravitation())
                .image(planet.getImage())
                .build();
    }

}
