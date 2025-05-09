package com.fotaleza.fortalezaapi.service.impl;


import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.repository.ProductRepository;
import com.fotaleza.fortalezaapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        if (productRepository.existsByCode(product.getCode()) || productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("El codigo del producto ya existe.");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, Product product) {
        Product productToUpdate =productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));

        productToUpdate.setName(product.getName());
        productToUpdate.setCode(product.getCode());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setCost(product.getCost());
        productToUpdate.setStock(product.getStock());
        productToUpdate.setMinimumStock(product.getMinimumStock());
        productToUpdate.setSubcategory(product.getSubcategory());
        productToUpdate.setSuppliers(product.getSuppliers());
        productToUpdate.setIsActivate(product.getIsActivate());

        return productRepository.save(productToUpdate);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product =productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));
        product.setIsActivate(false);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getActiveProducts() {
        return productRepository.findAll()
                .stream()
                .filter(Product::getIsActivate)
                .toList();
    }
}
