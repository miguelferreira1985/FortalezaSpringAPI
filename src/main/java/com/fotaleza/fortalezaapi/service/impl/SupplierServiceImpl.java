package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.repository.SupplierRepository;
import com.fotaleza.fortalezaapi.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public List<Supplier> getAllSuppliers(Boolean isActivate) {
        return supplierRepository
                .findAll()
                .stream()
                .filter(supplier -> supplier.getIsActivate().equals(isActivate))
                .toList();
    }

    @Override
    public Supplier getSupplierById(Integer supplierId) {
        return supplierRepository.findById(supplierId).orElse(null);
    }

    @Override
    public Supplier getSupplierByName(String supplierName) {
        return supplierRepository.findByName(supplierName)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con el nombre: " + supplierName));
    }

    @Override
    public Boolean existsBySupplierName(String supplierName) {
        return supplierRepository.existsByName(supplierName);
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(Integer supplierId) {
        supplierRepository.deleteById(supplierId);
    }
}
