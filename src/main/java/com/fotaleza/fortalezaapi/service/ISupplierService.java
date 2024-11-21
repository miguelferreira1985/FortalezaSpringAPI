package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Supplier;

import java.util.List;

public interface ISupplierService {

    Supplier saveSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Integer supplierId);
    Supplier getSupplierByName(String supplierName);
    Boolean existsBySupplierName(String supplierName);
    Supplier updateSupplier(Supplier supplier);
    void deleteSupplier(Integer supplierId);

}
