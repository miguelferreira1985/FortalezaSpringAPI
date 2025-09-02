package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);
    Optional<Product> findByCode(String code);
    Optional<Product> findByNameOrCode(String name, String code);
    List<Product> findByIsActivate(Boolean isActivate);

}
