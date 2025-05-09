package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Product createProduct(Product product);
    Product updateProduct(Integer productId, Product product);
    void deleteProduct(Integer productId);
    Optional<Product> getProductById(Integer productId);
    List<Product> getAllProducts();
    Optional<Product> getProductByCode(String code);
    List<Product> getActiveProducts();

}
