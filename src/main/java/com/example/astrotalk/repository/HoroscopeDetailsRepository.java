package com.example.astrotalk.repository;

import com.example.astrotalk.dto.HoroscopeDetailsDto;
import com.example.astrotalk.entity.planet.HoroscopeDetails;
import com.example.astrotalk.entity.planet.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoroscopeDetailsRepository extends JpaRepository<HoroscopeDetails, Long> {
    List<HoroscopeDetailsDto> findByHoroscopeIdAndType(long horoscopeId, Type type);
}
