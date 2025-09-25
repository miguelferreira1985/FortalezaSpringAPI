package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByNameOrCode(String name, String code);
    List<Product> findByIsActivate(Boolean isActivate);

    @Query("SELECT p FROM Product p WHERE (p.name = :name OR p.code = :code) AND p.id != :id")
    Optional<Product> findByNameOrCodeAndIdNot(@Param("name") String name, @Param("code") String code, @Param("id") Integer id);

    @Query("SELECT SUM(CAST(p.stock as java.math.BigDecimal) * CAST(p.cost as java.math.BigDecimal)) FROM Product p")
    BigDecimal calculateTotalInventoryValue();
}
