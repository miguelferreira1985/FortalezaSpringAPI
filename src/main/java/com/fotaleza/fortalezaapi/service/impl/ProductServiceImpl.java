package com.fotaleza.fortalezaapi.service.impl;


import com.fotaleza.fortalezaapi.dto.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.ProductResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ProductMapper;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.model.Supplier;
import com.fotaleza.fortalezaapi.repository.ProductRepository;
import com.fotaleza.fortalezaapi.repository.SubcategoryRepository;
import com.fotaleza.fortalezaapi.repository.SupplierRepository;
import com.fotaleza.fortalezaapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        validateNameOrCodeUnique(productRequestDTO.getName(), productRequestDTO.getCode(), null);

        Product product = productMapper.toEntity(productRequestDTO);

        Subcategory subcategory = subcategoryRepository.findById(productRequestDTO.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria no encontrada."));
        product.setSubcategory(subcategory);

        Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(productRequestDTO.getSupplierIds()));
        product.setSuppliers(suppliers);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO productRequestDTO) {
        Product productToUpdate =productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));

        validateNameOrCodeUnique(productRequestDTO.getName(), productRequestDTO.getCode(), productId);

        productMapper.updateEntityFromRequestDTO(productRequestDTO, productToUpdate);

        Subcategory subcategory = subcategoryRepository.findById(productRequestDTO.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria no encontrada."));
        productToUpdate.setSubcategory(subcategory);

        Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(productRequestDTO.getSupplierIds()));
        productToUpdate.setSuppliers(suppliers);

        Product updatedProduct = productRepository.save(productToUpdate);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));
        product.setIsActivate(false);
        productRepository.save(product);
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));
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
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));
        product.setIsActivate(true);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO deactivateProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));
        product.setIsActivate(false);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(savedProduct);
    }

    private void validateNameOrCodeUnique(String name, String code, Integer productId) {
        productRepository.findByNameOrCode(name, code)
                .ifPresent(p -> {
                    if (productId == null || p.getId().equals(productId)) {
                        throw new ResourceAlreadyExistsException("El producto con el nombre o codigo ya existe.;");
                    }
                });
    }

}
