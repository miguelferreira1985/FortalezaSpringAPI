package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    Optional<Presentation> findByNameOrAbbreviation(String name, String abbreviation);

    @Query("SELECT p FROM Presentation p WHERE (p.name = :name OR p.abbreviation = :abbreviation) AND p.id != :id")
    Optional<Presentation> findByNameOrAbbreviationAndIdNot(@Param("name") String presentationName, @Param("abbreviation") String abbreviation, @Param("id") Integer presentationId);

}
