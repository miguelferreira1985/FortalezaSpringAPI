package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.model.ECategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
