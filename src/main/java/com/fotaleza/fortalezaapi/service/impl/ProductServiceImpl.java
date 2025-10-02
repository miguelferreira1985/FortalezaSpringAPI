package com.fotaleza.fortalezaapi.service.impl;


import com.fotaleza.fortalezaapi.constants.ErrorMessages;
import com.fotaleza.fortalezaapi.dto.request.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;
import com.fotaleza.fortalezaapi.enums.EMovementType;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ProductMapper;
import com.fotaleza.fortalezaapi.model.Presentation;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.repository.*;
import com.fotaleza.fortalezaapi.service.IInventoryMovementService;
import com.fotaleza.fortalezaapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final PresentationRepository presentationRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    private final IInventoryMovementService inventoryMovementService;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        validateNameOrCodeUnique(productRequestDTO.getName(), productRequestDTO.getCode(), null);

        Product product = productMapper.toEntity(productRequestDTO);

        Subcategory subcategory = subcategoryRepository.findById(productRequestDTO.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_SUBCATEGORY_NOT_FOUND));
        product.setSubcategory(subcategory);

        Presentation presentation = presentationRepository.findById(productRequestDTO.getPresentationId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_PRESENTATION_NOT_FOUND));
        product.setPresentation(presentation);

        Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(productRequestDTO.getSupplierIds()));
        product.setSuppliers(suppliers);



        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO productRequestDTO) {
        Product productToUpdate =productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));

        validateNameOrCodeUnique(productRequestDTO.getName(), productRequestDTO.getCode(), productId);

        productMapper.updateEntityFromRequestDTO(productRequestDTO, productToUpdate);

        Subcategory subcategory = subcategoryRepository.findById(productRequestDTO.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_SUBCATEGORY_NOT_FOUND));
        productToUpdate.setSubcategory(subcategory);

        Presentation presentation = presentationRepository.findById(productRequestDTO.getPresentationId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_PRESENTATION_NOT_FOUND));
        productToUpdate.setPresentation(presentation);

        Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(productRequestDTO.getSupplierIds()));
        productToUpdate.setSuppliers(suppliers);

        Product updatedProduct = productRepository.save(productToUpdate);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
        return productMapper.toResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts(Boolean isActivate) {
        List<Product> products = Optional.ofNullable(isActivate)
                .map(productRepository::findByIsActivate)
                .orElseGet(productRepository::findAll);
        return productMapper.toResponseDTOList(products);
    }

    @Override
    @Transactional
    public ProductResponseDTO activateProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
        product.setIsActivate(true);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO deactivateProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));
        product.setIsActivate(false);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public BigDecimal getInventoryValue() {
        return productRepository.calculateTotalInventoryValue();
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProductStock(Integer productId, BigDecimal quantity, String description) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PRODUCT_NOT_FOUND, productId)));

        BigDecimal previousStock = product.getStock();
        BigDecimal newStock = previousStock.add(quantity);

        if (newStock.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("El stock no puede ser negativo");
        }

        product.setStock(newStock);

        Product productToUpdate = productRepository.save(product);

        inventoryMovementService.recordMovement(product, quantity, EMovementType.AJUSTE, previousStock, newStock, description);

        return productMapper.toResponseDTO(productToUpdate);
    }


    private void validateNameOrCodeUnique(String name, String code, Integer productId) {

        final String errorMessage = String.format(ErrorMessages.PRODUCT_ALREADY_EXISTS, name.toUpperCase(), code.toUpperCase());

        if (productId == null) {
            productRepository.findByNameOrCode(name, code)
                    .ifPresent(p -> {
                            throw new ResourceAlreadyExistsException(errorMessage);
                    });
        } else {
            productRepository.findByNameOrCodeAndIdNot(name, code, productId)
                    .ifPresent(p -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        }

    }

}
