package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    Optional<Subcategory> findByName(String subcategoryName);

    @Query("SELECT s FROM Subcategory s WHERE s.name = :name AND s.id != :id")
    Optional<Subcategory> findByNameAndNotId(
            @Param("name") String subcategoryName,
            @Param("id") Integer subcatetgoryId);

}
