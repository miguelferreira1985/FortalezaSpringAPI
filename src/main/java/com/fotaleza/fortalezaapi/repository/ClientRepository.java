package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByRfc(String rfc);
    List<Client> findByIsActivate(Boolean isActivate);
}
