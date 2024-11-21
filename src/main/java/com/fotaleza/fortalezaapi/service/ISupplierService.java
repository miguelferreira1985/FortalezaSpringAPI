package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface ISupplierService {

    Supplier saveSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Integer supplierId);
    Optional<Supplier> getSupplierByName(String supplierName);
    Boolean existsBySupplierName(String supplierName);
    Supplier updateSupplier(Supplier supplier);
    void deleteSupplier(Integer supplierId);

}
