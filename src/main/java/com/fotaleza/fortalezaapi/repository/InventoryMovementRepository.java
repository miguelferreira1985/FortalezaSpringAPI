package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.InventoryMovement;
import com.fotaleza.fortalezaapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Integer> {

    List<InventoryMovement> findByProduct(Product product);
    List<InventoryMovement> findByProductOrderByMovementDateDesc(Product product);

}
