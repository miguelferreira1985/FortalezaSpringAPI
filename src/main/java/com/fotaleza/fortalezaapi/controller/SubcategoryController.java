package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.SubcategoryRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.SubcategoryResponseDTO;
import com.fotaleza.fortalezaapi.service.ISubcategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {

    private final ISubcategoryService subcategoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> createSubcategory(@Valid @RequestBody SubcategoryRequestDTO subcategoryRequestDTO) {

        SubcategoryResponseDTO createdSubcategory = subcategoryService.createSubcategory(subcategoryRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<SubcategoryResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message(String.format("La subcategoría %s ha sido agregada.", subcategoryRequestDTO.getName().toUpperCase()))
                        .data(createdSubcategory)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> updateSubcategory(@PathVariable Integer id,
                                                 @Valid @RequestBody SubcategoryRequestDTO subcategoryRequestDTO) {

        SubcategoryResponseDTO updatedSubcategory = subcategoryService.updateSubcategory(id, subcategoryRequestDTO);

        return ResponseEntity.ok(
                ApiResponse.<SubcategoryResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message(String.format("La subcategoría %s ha sido actualizada.", subcategoryRequestDTO.getName().toUpperCase()))
                        .data(updatedSubcategory)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubcategory(@PathVariable Integer id) {

        subcategoryService.deleteSubcategory(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("La subcategoría fue eliminada.")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> getSubcategoryById(@PathVariable Integer id) {

        SubcategoryResponseDTO subcategory = subcategoryService.getSubcategoryById(id);

        return ResponseEntity.ok(
                ApiResponse.<SubcategoryResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Subcategoría obtenida existosamente.")
                        .data(subcategory)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubcategoryResponseDTO>>> getAllSubcategories() {

        List<SubcategoryResponseDTO> subcategories = subcategoryService.getAllSubcategories();

        return ResponseEntity.ok(
                ApiResponse.<List<SubcategoryResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Subcategorias obtenidas existosamente.")
                        .data(subcategories)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
