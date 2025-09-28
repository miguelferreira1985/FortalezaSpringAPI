package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name = :name AND c.id != :id")
    Optional<Category> findByNameAndIdNot(@Param("name") String categoryName, @Param("id") Integer categoryId);

}
