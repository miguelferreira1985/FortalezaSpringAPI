package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.SupplierRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.SupplierResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ProductMapper;
import com.fotaleza.fortalezaapi.mapper.SupplierMapper;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.model.SupplierProduct;
import com.fotaleza.fortalezaapi.repository.ProductRepository;
import com.fotaleza.fortalezaapi.repository.SupplierProductRepository;
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
    private final SupplierProductRepository supplierProductRepository;
    private final ProductRepository productRepository;
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
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor que desea actualizar no existe."));

        validateNameUnique(supplierRequestDTO.getName(), supplierId);

        supplierMapper.updateEntityFromRequestDTO(supplierRequestDTO, supplierToUpdate);

        Supplier updatedSupplier = supplierRepository.save(supplierToUpdate);
        return supplierMapper.toResponseDTO(updatedSupplier);
    }

    @Override
    @Transactional
    public SupplierResponseDTO deactivateSupplierAndReassignProducts(Integer supplierId) {
        Supplier supplierToDeactivate = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor que desea desactivar no existe."));

        supplierToDeactivate.setIsActivate(false);

        Supplier supplierDeactivated = supplierRepository.save(supplierToDeactivate);

        supplierProductRepository.deleteAll(supplierProductRepository.findBySupplierId(supplierId));

        return supplierMapper.toResponseDTO(supplierDeactivated);
    }

    @Override
    @Transactional
    public SupplierResponseDTO activateSupplier(Integer supplierId) {
        Supplier supplierToActivate = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("El proveedor que desea activar no existe."));
        supplierToActivate.setIsActivate(false);

        Supplier supplierActivated = supplierRepository.save(supplierToActivate);

        return supplierMapper.toResponseDTO(supplierActivated);
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

        List<SupplierProduct> supplierProducts = supplierProductRepository.findBySupplierId(supplierId);

        return supplierProducts.stream()
                .map(SupplierProduct::getProduct)
                .filter(Product::getIsActivate)
                .map(productMapper::toResponseDTO)
                .toList();
    }

    private void validateNameUnique(String name, Integer supplierId) {

        final String errorMessage = String.format("Ya existe un proveedor con el nombre %s.", name.toUpperCase());

        if (supplierId == null) {
            supplierRepository.findByName(name)
                    .ifPresent(s -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        } else {
            supplierRepository.findByNameAndIdNot(name, supplierId)
                    .ifPresent(s -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        }
    }

}
