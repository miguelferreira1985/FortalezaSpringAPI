package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsByRfc(String rfc);
}
