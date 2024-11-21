package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    Optional<Presentation> findByName(String presentationName);
    Boolean existsByName(String presentationName);

}
