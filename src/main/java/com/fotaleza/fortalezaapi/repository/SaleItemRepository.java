package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {
}
