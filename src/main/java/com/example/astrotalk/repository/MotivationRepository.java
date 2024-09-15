package com.example.astrotalk.repository;

import com.example.astrotalk.entity.planet.Motivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivationRepository extends JpaRepository<Motivation, Long> {
}
