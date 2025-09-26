package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.CashCut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashCutRepository extends JpaRepository<CashCut, Integer> {
}
