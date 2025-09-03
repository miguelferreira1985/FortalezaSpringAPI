package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.ProductDTO;
import com.fotaleza.fortalezaapi.dto.SupplierDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ProductMapper;
import com.fotaleza.fortalezaapi.mapper.SupplierMapper;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.repository.SupplierRepository;
import com.fotaleza.fortalezaapi.service.ISupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements ISupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final ProductMapper productMapper;

    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        supplierRepository.findByName(supplierDTO.getName())
                .ifPresent(p -> {
                    throw new ResourceAlreadyExistsException("El proveedor con el nombre ya existe.");
                });
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    @Override
    public SupplierDTO updateSupplier(Integer supplierId, SupplierDTO supplierDTO) {
        Supplier supplierToUpdate = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));

        supplierRepository.findByName(supplierDTO.getName())
                .ifPresent(p -> {
                    if (!p.getId().equals(supplierId)) {
                        throw new ResourceAlreadyExistsException("El proveedor con el nombre ya existe.");
                    }
                });

        supplierMapper.updateEntityFromDTO(supplierDTO, supplierToUpdate);

        Supplier updatedSupplier = supplierRepository.save(supplierToUpdate);
        return supplierMapper.toDTO(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));
        supplier.setIsActivate(false);
        supplierRepository.save(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierDTO getSupplierById(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));
        return supplierMapper.toDTO(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierDTO> getAllSuppliers(Boolean isActivate) {
        List<Supplier> suppliers;

        if (isActivate != null) {
            suppliers = supplierRepository.findByIsActivate(isActivate);
        } else {
            suppliers = supplierRepository.findAll();
        }
        return supplierMapper.toDTOList(suppliers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsOfSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));

        return supplier.getProducts().stream()
                .filter(Product::getIsActivate)
                .map(productMapper::toDTO)
                .toList();
    }

}
