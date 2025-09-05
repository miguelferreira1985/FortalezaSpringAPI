package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.CashOutFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashOutFlowRepository extends JpaRepository<CashOutFlow, Integer> {

    List<CashOutFlow> findByCashStartId(Integer cashStartId);

}
