package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO productRequestDTO);
    void deleteProduct(Integer productId);
    ProductResponseDTO getProductById(Integer productId);
    List<ProductResponseDTO> getAllProducts(Boolean isActivate);
    ProductResponseDTO activateProduct(Integer productId);
    ProductResponseDTO deactivateProduct(Integer productId);
    BigDecimal getInventoryValue();

}
