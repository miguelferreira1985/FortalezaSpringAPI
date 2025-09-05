package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.ProductResponseDTO;

import java.util.List;

public interface IProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO productRequestDTO);
    void deleteProduct(Integer productId);
    ProductResponseDTO getProductById(Integer productId);
    List<ProductResponseDTO> getAllProducts(Boolean isActivate);
    ProductResponseDTO activateProduct(Integer productId);
    ProductResponseDTO deactivateProduct(Integer productId);

}
