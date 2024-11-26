package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.EPresentation;
import com.fotaleza.fortalezaapi.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    Optional<Presentation> findByName(EPresentation presentationName);

}
