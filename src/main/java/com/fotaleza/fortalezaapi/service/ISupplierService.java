package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface ISupplierService {

    Supplier createSupplier(Supplier supplier);
    Supplier updateSupplier(Integer supplierId, Supplier supplier);
    void deleteSupplier(Integer supplierId);
    Optional<Supplier> getSupplierById(Integer supplierId);
    List<Supplier> getAllSuppliers();
    List<Supplier> getActiveSuppliers();

}
