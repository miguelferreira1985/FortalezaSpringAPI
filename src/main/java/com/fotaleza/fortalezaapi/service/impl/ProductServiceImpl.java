package com.fotaleza.fortalezaapi.service.impl;


import com.fotaleza.fortalezaapi.dto.ProductDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        productRepository.findByNameOrCode(productDTO.getName(), productDTO.getCode())
                .ifPresent(p -> {
                    throw new ResourceAlreadyExistsException("El producto con el código o nomre ya existe.");
                });

        Product product = productMapper.toEntity(productDTO);

        Subcategory subcategory = subcategoryRepository.findById(productDTO.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria no encontrada."));
        product.setSubcategory(subcategory);

        Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(productDTO.getSupplierIds()));
        product.setSuppliers(suppliers);

        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Integer productId, ProductDTO productDTO) {
        Product productToUpdate =productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));

        productRepository.findByNameOrCode(productDTO.getName(), productDTO.getCode())
                .ifPresent(p -> {
                    if (!p.getId().equals(productId)) {
                        throw new ResourceAlreadyExistsException("El producto con el codigo o nombre ya existe.");
                    }
                });

        productMapper.updateEntityFromDTO(productDTO, productToUpdate);

        Subcategory subcategory = subcategoryRepository.findById(productDTO.getSubcategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoria no encontrada."));
        productToUpdate.setSubcategory(subcategory);

        Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(productDTO.getSupplierIds()));
        productToUpdate.setSuppliers(suppliers);

        Product updatedProduct = productRepository.save(productToUpdate);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product =productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe"));
        product.setIsActivate(false);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("El producto no existe."));
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts(Boolean isActivate) {

        List<Product> products;

        if(isActivate != null) {
            products = productRepository.findByIsActivate(isActivate);
        } else {
            products = productRepository.findAll();
        }
        return productMapper.toDTOList(products);
    }

}
