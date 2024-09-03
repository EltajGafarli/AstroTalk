package com.example.astrotalk.service;

import com.example.astrotalk.dto.HoroscopeDetailsDto;
import com.example.astrotalk.entity.planet.Horoscope;
import com.example.astrotalk.entity.planet.HoroscopeDetails;
import com.example.astrotalk.entity.planet.Type;
import com.example.astrotalk.exception.NotFoundException;
import com.example.astrotalk.repository.HoroscopeDetailsRepository;
import com.example.astrotalk.repository.HoroscopeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoroscopeDetailsService {
    private HoroscopeDetailsRepository horoscopeDetailsRepository;
    private HoroscopeRepository horoscopeRepository;

    @Transactional
    public String create(long hId, HoroscopeDetailsDto dto) {
        Horoscope horoscope = horoscopeRepository.findById(hId)
                .orElseThrow(
                        () -> new NotFoundException("Horoscope Details not found")
                );

        HoroscopeDetails horoscopeDetails = HoroscopeDetails
                .builder()
                .features(dto.getFeatures())
                .planet(dto.getPlanet())
                .type(dto.getType())
                .build();

        horoscopeDetails.setHoroscope(horoscope);
        horoscope.addDetails(horoscopeDetails);

        this.horoscopeDetailsRepository.save(horoscopeDetails);

        return "HoroscopeDetails created";

    }

    public List<HoroscopeDetailsDto> findByHoroscopeId(long hId) {
        Horoscope horoscope = horoscopeRepository.findById(hId)
                .orElseThrow(
                        () -> new NotFoundException("Horoscope not found")
                );

        return horoscope.getHoroscopeDetails()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<HoroscopeDetailsDto> findDailyHoroscope(long horoscopeId) {
        return horoscopeDetailsRepository.findByHoroscopeIdAndType(horoscopeId, Type.DAILY);
    }

    public List<HoroscopeDetailsDto> findWeeklyHoroscope(long horoscopeId) {
        return horoscopeDetailsRepository.findByHoroscopeIdAndType(horoscopeId, Type.WEEKLY);
    }

    public List<HoroscopeDetailsDto> findMonthlyHoroscope(long horoscopeId) {
        return horoscopeDetailsRepository.findByHoroscopeIdAndType(horoscopeId, Type.MONTHLY);
    }

    private HoroscopeDetailsDto toDto(HoroscopeDetails details) {
        return HoroscopeDetailsDto
                .builder()
                .features(details.getFeatures())
                .planet(details.getPlanet())
                .type(details.getType())
                .build();
    }
}
