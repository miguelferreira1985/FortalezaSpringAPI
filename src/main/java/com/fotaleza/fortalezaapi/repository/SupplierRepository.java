package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Optional<Supplier> findByName(String supplierName);
    List<Supplier> findByIsActivate(Boolean isActivate);

    @Query("SELECT s FROM Supplier s WHERE s.name = :name AND s.id != :id")
    Optional<Supplier> findByNameAndIdNot(
            @Param("name") String supplierName,
            @Param("id") Integer supplierId);
}
