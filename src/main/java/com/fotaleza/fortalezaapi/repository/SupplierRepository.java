package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Optional<Supplier> findByName(String supplierName);
    Boolean existsByName(String supplierName);

}
