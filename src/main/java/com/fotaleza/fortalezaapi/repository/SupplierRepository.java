package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Optional<Supplier> findByName(String supplierName);
    Boolean existsByName(String supplierName);

}
