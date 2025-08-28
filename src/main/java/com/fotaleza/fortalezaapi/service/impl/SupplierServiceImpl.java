package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.repository.SupplierRepository;
import com.fotaleza.fortalezaapi.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements ISupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier createSupplier(Supplier supplier) {
        if (supplierRepository.existsByName(supplier.getName())) {
            throw new IllegalArgumentException("El proveedor ya existe.")
        }
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Integer supplierId, Supplier supplier) {
        Supplier supplierToUpdate = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("El proveedor no existe."));

        supplierToUpdate.setName(supplier.getName());
        supplierToUpdate.setContact(supplier.getContact());
        supplierToUpdate.setAddress(supplier.getAddress());
        supplierToUpdate.setEmail(supplier.getEmail());
        supplierToUpdate.setPhone(supplier.getPhone());
        supplierToUpdate.setIsActivate(supplier.getIsActivate());

        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("El proveedor no existe."));
        supplier.setIsActivate(false);
        supplierRepository.deleteById(supplierId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierById(Integer supplierId) { return supplierRepository.findById(supplierId); }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getAllSuppliers() { return supplierRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getActiveSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .filter(Supplier::getIsActivate)
                .toList();
    }
}
