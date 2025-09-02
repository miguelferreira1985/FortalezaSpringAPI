package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.SupplierDTO;
import com.fotaleza.fortalezaapi.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface ISupplierService {

    SupplierDTO createSupplier(SupplierDTO supplierDTO);
    SupplierDTO updateSupplier(Integer supplierId, SupplierDTO supplierDTO);
    void deleteSupplier(Integer supplierId);
    SupplierDTO getSupplierById(Integer supplierId);
    List<SupplierDTO> getAllSuppliers(Boolean isActivate);

}
