package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.StockUpdateRequestDTO;
import com.fotaleza.fortalezaapi.dto.request.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;
import com.fotaleza.fortalezaapi.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints para gestión de productos en el sistema POS")
public class ProductController {

    private final IProductService productService;

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el sistema")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {

        ProductResponseDTO createdProduct = productService.createProduct(productRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ProductResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Producto creado con existosamente.")
                        .data(createdProduct)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Integer id,
            @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productRequestDTO);

        return ResponseEntity.ok(
                ApiResponse.<ProductResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Producto actualizado existosamente.")
                        .data(updatedProduct)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Producto eliminado existosamente.")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(@PathVariable Integer id) {
        ProductResponseDTO product = productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.<ProductResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Producto obtenido existosamente.")
                        .data(product)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @Operation(summary = "Obetener todos los productos", description = "Devuelve uan lista de todos los productos registrados en el sistema.")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts(
            @RequestParam(name = "isActivate", required = false) Boolean isActivate) {
        List<ProductResponseDTO> products = productService.getAllProducts(isActivate);

        return ResponseEntity.ok(
                ApiResponse.<List<ProductResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Productos obtenidos existosamente.")
                        .data(products)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponseDTO>> getLowStockProducts() {
        List<ProductResponseDTO> allProducts = productService.getAllProducts(true);
        List<ProductResponseDTO> lowStock = allProducts.stream()
                .filter(ProductResponseDTO::getIsBelowOrEqualMinimumStock)
                .toList();
        return ResponseEntity.ok(lowStock);
    }

    @GetMapping("/inventory-value")
    public ResponseEntity<BigDecimal> getInventoryValue() {
        BigDecimal totalValue = productService.getInventoryValue();
        return ResponseEntity.ok(totalValue);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProductResponseDTO> activateProduct(@PathVariable Integer id) {
        ProductResponseDTO product = productService.activateProduct(id);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<ProductResponseDTO> deactivateProduct(@PathVariable Integer id) {
        ProductResponseDTO product = productService.deactivateProduct(id);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> deactivateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody StockUpdateRequestDTO stockUpdateRequestDTO) {

        ProductResponseDTO updatedProduct = productService.updateProductStock(id, stockUpdateRequestDTO.getQuantity());

        return ResponseEntity.ok(
                ApiResponse.<ProductResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Stock actualizado exitosamente.")
                        .data(updatedProduct)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
