package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByNameOrRfc(String name, String rfc);
    List<Client> findByIsActivate(Boolean isActivate);

    @Query("SELECT c FROM Client c WHERE (c.name = :name OR c.rfc = :rfc) AND c.id != :id")
    Optional<Client> findByNameOrRfcAndIdNot(
            @Param("name") String clientName,
            @Param("rfc") String rfc,
            @Param("id") Integer clientId);



}
