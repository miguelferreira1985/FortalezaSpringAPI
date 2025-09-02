package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.ProductDTO;
import com.fotaleza.fortalezaapi.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Integer productId, ProductDTO productDTO);
    void deleteProduct(Integer productId);
    ProductDTO getProductById(Integer productId);
    List<ProductDTO> getAllProducts(Boolean isActivate);
    ProductDTO getProductByCode(String code);

}
