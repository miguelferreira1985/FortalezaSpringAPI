package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

}
