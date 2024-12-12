package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {

    Optional<Color> findByName(String colorName);
    Boolean existsByName(String colorName);

}
