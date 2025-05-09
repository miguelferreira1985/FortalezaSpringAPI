package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.ProductDTO;
import com.fotaleza.fortalezaapi.mapper.ProductMapper;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product productToSave = productMapper.toEntity(productDTO);
        Product savedProduct = productService.createProduct(productToSave);

        return ResponseEntity.created(URI.create("/api/v1/products" + savedProduct.getId()))
                .body(productMapper.toDTO(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id,
                                             @Valid @RequestBody ProductDTO productDTO) {
        Product productUpdated = productService.updateProduct(id, productMapper.toEntity(productDTO));
        return ResponseEntity.ok(productMapper.toDTO(productUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        Optional<Product> productOptional = productService.getProductById(id);

        return productOptional.map(product -> ResponseEntity.ok(productMapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts()
                .stream()
                .map(productMapper::toDTO)
                .toList();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/active")
    private ResponseEntity<List<ProductDTO>> getActivateProducts() {
        List<ProductDTO> activeProducts = productService.getActiveProducts()
                .stream()
                .map(productMapper::toDTO)
                .toList();

        return ResponseEntity.ok().body(activeProducts);
    }
}
