package com.fotaleza.fortalezaapi.service.impl;


import com.fotaleza.fortalezaapi.dto.ProductDTO;
import com.fotaleza.fortalezaapi.exception.ProductAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ProductNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ProductMapper;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.repository.ProductRepository;
import com.fotaleza.fortalezaapi.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        productRepository.findByNameOrCode(productDTO.getName(), productDTO.getCode())
                .ifPresent(p -> {
                    throw new ProductAlreadyExistsException("El producto con el código o nomre ya existe.");
                });
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Integer productId, ProductDTO productDTO) {
        Product productToUpdate =productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("El producto no existe"));

        productRepository.findByNameOrCode(productDTO.getName(), productDTO.getCode())
                .ifPresent(p -> {
                    throw new ProductAlreadyExistsException("El producto con el código o nomre ya existe.");
                });

        productMapper.updateEntityFromDTO(productDTO, productToUpdate);

        Product updatedProduct = productRepository.save(productToUpdate);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product =productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("El producto no existe"));
        product.setIsActivate(false);
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("El producto no existe."));
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
