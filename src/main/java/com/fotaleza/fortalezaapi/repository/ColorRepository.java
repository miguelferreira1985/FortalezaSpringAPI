package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Integer> {

    Optional<Color> findByName(String colorName);
    Boolean existsByName(String colorName);

}
