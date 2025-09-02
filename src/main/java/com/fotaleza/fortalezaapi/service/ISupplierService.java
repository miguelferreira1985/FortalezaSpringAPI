package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.SupplierDTO;

import java.util.List;

public interface ISupplierService {

    SupplierDTO createSupplier(SupplierDTO supplierDTO);
    SupplierDTO updateSupplier(Integer supplierId, SupplierDTO supplierDTO);
    void deleteSupplier(Integer supplierId);
    SupplierDTO getSupplierById(Integer supplierId);
    List<SupplierDTO> getAllSuppliers(Boolean isActivate);

}
