package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.SubcategoryDTO;
import com.fotaleza.fortalezaapi.mapper.SubcategoryMapper;
import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.service.impl.SubcategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {

    private final SubcategoryServiceImpl subcategoryService;
    private final SubcategoryMapper subcategoryMapper;

    @PostMapping
    public ResponseEntity<SubcategoryDTO> createSubcategory(@Valid @RequestBody SubcategoryDTO subCategoryDTO) {
        Subcategory subcategoryToSave = subcategoryMapper.toEntity(subCategoryDTO);
        Subcategory subcategorySaved = subcategoryService.createSubcategory(subcategoryToSave);

        return ResponseEntity.created(URI.create("/api/v1/subcategories" + subcategorySaved.getId()))
                .body(subcategoryMapper.toDto(subcategorySaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> updateSubcategory(@PathVariable Integer id,
                                                 @Valid @RequestBody SubcategoryDTO subcategoryDTO) {
        Subcategory subcategoryUpdated = subcategoryService.updateSubcategory(id, subcategoryMapper.toEntity(subcategoryDTO));
        return ResponseEntity.ok(subcategoryMapper.toDto(subcategoryUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Integer id) {
        subcategoryService.deleteSubcategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> getSubcategoryById(@PathVariable Integer id) {
        Optional<Subcategory> subcategoryOptional = subcategoryService.getSubcategoryById(id);

        return subcategoryOptional.map(subcategory -> ResponseEntity.ok(subcategoryMapper.toDto(subcategory)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<SubcategoryDTO>> getAllSubcategories() {
        List<SubcategoryDTO> subcategories = subcategoryService.getAllSubcategories()
                .stream()
                .map(subcategoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/active")
    public ResponseEntity<List<SubcategoryDTO>> getActiveSubcategories() {
        List<SubcategoryDTO> subcategories = subcategoryService.getActiveSubcategories()
                .stream()
                .map(subcategoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(subcategories);
    }
}
