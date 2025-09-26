package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.SubcategoryRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.SubcategoryResponseDTO;
import com.fotaleza.fortalezaapi.service.ISubcategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {

    private final ISubcategoryService subcategoryService;

    @PostMapping
    public ResponseEntity<SubcategoryResponseDTO> createSubcategory(@Valid @RequestBody SubcategoryRequestDTO subcategoryRequestDTO) {
        SubcategoryResponseDTO createdSubcategory = subcategoryService.createSubcategory(subcategoryRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdSubcategory.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdSubcategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoryResponseDTO> updateSubcategory(@PathVariable Integer id,
                                                 @Valid @RequestBody SubcategoryRequestDTO subcategoryRequestDTO) {
        SubcategoryResponseDTO updatedSubcategory = subcategoryService.updateSubcategory(id, subcategoryRequestDTO);
        return ResponseEntity.ok(updatedSubcategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Integer id) {
        subcategoryService.deleteSubcategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryResponseDTO> getSubcategoryById(@PathVariable Integer id) {
        SubcategoryResponseDTO subcategory = subcategoryService.getSubcategoryById(id);
        return ResponseEntity.ok(subcategory);
    }

    @GetMapping
    public ResponseEntity<List<SubcategoryResponseDTO>> getAllSubcategories(@RequestParam(name = "isACtivate", required = false) Boolean isActivate) {
        List<SubcategoryResponseDTO> subcategories = subcategoryService.getAllSubcategories(isActivate);
        return ResponseEntity.ok(subcategories);
    }

}
