package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    Optional<Subcategory> findByName(String subcategoryName);
    List<Subcategory> findByIsActivate(Boolean isActivate);

}
