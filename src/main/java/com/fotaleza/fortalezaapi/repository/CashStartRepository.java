package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.CashStart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashStartRepository extends JpaRepository<CashStart, Integer> {

    Optional<CashStart> findByEndDateTimeIsNull();

}
