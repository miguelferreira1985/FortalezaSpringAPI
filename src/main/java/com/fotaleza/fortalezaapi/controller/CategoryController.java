package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.CategoryRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.CategoryResponseDTO;
import com.fotaleza.fortalezaapi.service.ICategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Endpoints para gestión de categorias en el sistema de Inventario")
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {

        CategoryResponseDTO createdCategory = categoryService.createCategory(categoryRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CategoryResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Categoría creada existosamente.")
                        .data(createdCategory)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(@PathVariable Integer id,
                                                              @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, categoryRequestDTO);

        return ResponseEntity.ok(
                ApiResponse.<CategoryResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Categoría actualizada existosamente.")
                        .data(updatedCategory)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(@PathVariable Integer id) {

        CategoryResponseDTO category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(
                ApiResponse.<CategoryResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Categoría obtenida existosamente.")
                        .data(category)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAllCategories() {

        List<CategoryResponseDTO> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(
                ApiResponse.<List<CategoryResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Categorías obtenidas existosamente.")
                        .data(categories)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
