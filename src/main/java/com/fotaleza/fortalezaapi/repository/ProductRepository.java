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

    boolean existsByPresentationId(Integer presentationId);
    boolean existsBySubcategoryId(Integer subcategoryId);

    Optional<Product> findByNameOrCode(String name, String code);
    List<Product> findByIsActivate(Boolean isActivate);

    @Query("SELECT p FROM Product p WHERE (p.name = :name OR p.code = :code) AND p.id != :id")
    Optional<Product> findByNameOrCodeAndIdNot(@Param("name") String name, @Param("code") String code, @Param("id") Integer id);

    @Query("SELECT SUM(CAST(p.stock as java.math.BigDecimal) * CAST(p.cost as java.math.BigDecimal)) FROM Product p")
    BigDecimal calculateTotalInventoryValue();

    @Query("""
    SELECT p 
    FROM Product p
    LEFT JOIN FETCH p.supplierProducts sp
    LEFT JOIN FETCH sp.supplier s
    WHERE p.id = :id
    """)
    Optional<Product> findByIdWithSuppliers(@Param("id") Integer id);

    @Query("""
    SELECT DISTINCT p 
    FROM Product p
    LEFT JOIN FETCH p.supplierProducts sp
    LEFT JOIN FETCH sp.supplier s
    WHERE (:isActivate IS NULL OR p.isActivate = :isActivate)
""")
    List<Product> findAllWithSuppliersAndStatus(@Param("isActivate") Boolean isActivate);
}
