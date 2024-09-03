package com.example.astrotalk.service;

import com.example.astrotalk.dto.HoroscopeDto;
import com.example.astrotalk.entity.planet.Horoscope;
import com.example.astrotalk.exception.NotFoundException;
import com.example.astrotalk.repository.HoroscopeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoroscopeService {
    private final HoroscopeRepository horoscopeRepository;

    @Transactional
    public String createHoroscope(HoroscopeDto horoscopeDto, MultipartFile file) {
        String fileName = FileService.handleFileUpload(file, "horoscope");
        horoscopeDto.setImage(fileName);
        Horoscope horoscope = dtoToHoroscope(horoscopeDto);
        horoscopeRepository.save(horoscope);
        return "Planet created successfully";
    }

    public HoroscopeDto findHoroscopeById(long id) {
        Horoscope horoscope = horoscopeRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Horoscope not found")
                );
        return HoroscopeDto
                .builder()
                .horoscopeName(horoscope.getHoroscopeName())
                .image(horoscope.getImage())
                .build();
    }

    public List<HoroscopeDto> findAll() {
        return horoscopeRepository
                .findAll()
                .stream()
                .map(this::horoscopeToDto)
                .toList();
    }

    @Transactional
    public String updateHoroscope(long id, HoroscopeDto horoscopeDto) {
        Horoscope horoscope = horoscopeRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Horoscope not found")
                );
        if(horoscopeDto.getHoroscopeName() != null) {
            horoscope.setHoroscopeName(horoscopeDto.getHoroscopeName());
        }

        if(horoscopeDto.getImage() != null) {
            horoscope.setImage(horoscopeDto.getImage());
        }

        return "Horoscope updated";
    }

    @Transactional
    public String deleteById(long id) {
        horoscopeRepository.deleteById(id);
        return "Horoscope Deleted";
    }

    private HoroscopeDto horoscopeToDto(Horoscope horoscope) {
        return HoroscopeDto
                .builder()
                .horoscopeName(horoscope.getHoroscopeName())
                .image(horoscope.getImage())
                .build();
    }

    private Horoscope dtoToHoroscope(HoroscopeDto horoscopeDto) {
        return Horoscope
                .builder()
                .horoscopeName(horoscopeDto.getHoroscopeName())
                .image(horoscopeDto.getImage())
                .build();
    }

}
