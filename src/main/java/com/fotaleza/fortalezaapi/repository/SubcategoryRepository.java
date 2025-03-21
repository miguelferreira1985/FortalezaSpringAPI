package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    Optional<Subcategory> findByName(String subcategoryName);
    Boolean existsByName(String subcategoryName);

}
