package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String categoryName);
    Boolean existsByName(String categoryName);

}
