package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.SupplierRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.SupplierResponseDTO;

import java.util.List;

public interface ISupplierService {

    SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO);
    SupplierResponseDTO updateSupplier(Integer supplierId, SupplierRequestDTO supplierRequestDTO);
    SupplierResponseDTO getSupplierById(Integer supplierId);
    List<SupplierResponseDTO> getAllSuppliers(Boolean isActivate);
    List<ProductResponseDTO> getProductsOfSupplier(Integer supplierId);
    SupplierResponseDTO deactivateSupplierAndReassignProducts(Integer supplierId);
    SupplierResponseDTO activateSupplier(Integer supplierId);

}
