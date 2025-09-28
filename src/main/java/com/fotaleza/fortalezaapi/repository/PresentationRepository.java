package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

    Optional<Presentation> findByName(String name);

    @Query("SELECT p FROM Presentation p WHERE p.name = :name AND p.id != :id")
    Optional<Presentation> findByNameAndIdNot(@Param("name") String presentationName, @Param("id") Integer presentationId);

}
