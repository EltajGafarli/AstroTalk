package com.example.astrotalk.repository;

import com.example.astrotalk.entity.planet.Horoscope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoroscopeRepository extends JpaRepository<Horoscope, Long> {
}
