package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.ProductDTO;

import java.util.List;

public interface IProductService {

    /**
     * Crea un nuevo rpoducto y devuelve el DTO con todos los campos calculados
     */
    ProductDTO createProduct(ProductDTO productDTO);

    /**
     * Actualiza un producto existente y devuelve el DTO con los campos calculados
     */
    ProductDTO updateProduct(Integer productId, ProductDTO productDTO);

    /**
     * Elimina un producto por su ID
     */
    void deleteProduct(Integer productId);

    /**
     * Obtiene un productor por su Id, incluyendo campos calculados
     */
    ProductDTO getProductById(Integer productId);

    /**
     * Obtiene todos los productos activos/inactivos según el parámetro.
     * Los DTOs incluyen los campos calculados
     */
    List<ProductDTO> getAllProducts(Boolean isActivate);

}
