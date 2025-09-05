package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.ProductResponseDTO;
import com.fotaleza.fortalezaapi.dto.SupplierRequestDTO;
import com.fotaleza.fortalezaapi.dto.SupplierResponseDTO;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements ISupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO) {
        validateNameUnique(supplierRequestDTO.getName(), null);

        Supplier supplier = supplierMapper.toEntity(supplierRequestDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);

        return supplierMapper.toResponseDTO(savedSupplier);
    }

    @Override
    @Transactional
    public SupplierResponseDTO updateSupplier(Integer supplierId, SupplierRequestDTO supplierRequestDTO) {
        Supplier supplierToUpdate = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));

        validateNameUnique(supplierRequestDTO.getName(), supplierId);

        supplierMapper.updateEntityFromRequestDTO(supplierRequestDTO, supplierToUpdate);

        Supplier updatedSupplier = supplierRepository.save(supplierToUpdate);
        return supplierMapper.toResponseDTO(updatedSupplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));
        supplier.setIsActivate(false);
        supplierRepository.save(supplier);
    }

    @Override
    public SupplierResponseDTO getSupplierById(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));
        return supplierMapper.toResponseDTO(supplier);
    }

    @Override
    public List<SupplierResponseDTO> getAllSuppliers(Boolean isActivate) {
        List<Supplier> suppliers = Optional.ofNullable(isActivate)
                .map(supplierRepository::findByIsActivate)
                .orElseGet(supplierRepository::findAll);

        return supplierMapper.toResponseDTOList(suppliers);
    }

    @Override
    public List<ProductResponseDTO> getProductsOfSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor no existe."));

        return supplier.getProducts().stream()
                .filter(Product::getIsActivate)
                .map(productMapper::toResponseDTO)
                .toList();
    }

    private void validateNameUnique(String name, Integer supplierId) {
        supplierRepository.findByName(name)
                .ifPresent(s -> {
                    if (supplierId == null || s.getId().equals(supplierId)) {
                        throw new ResourceAlreadyExistsException("El proveedor con el nombre ya existe.");
                    }
                });
    }

}
