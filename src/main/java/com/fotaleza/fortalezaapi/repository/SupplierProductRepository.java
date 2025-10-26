package com.fotaleza.fortalezaapi.repository;

import com.fotaleza.fortalezaapi.model.SupplierProduct;
import com.fotaleza.fortalezaapi.model.SupplierProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, SupplierProductId> {
    List<SupplierProduct> findByProductId(Integer productId);
    List<SupplierProduct> findBySupplierId(Integer supplierId);
    void deleteByProductId(Integer productId);
}
