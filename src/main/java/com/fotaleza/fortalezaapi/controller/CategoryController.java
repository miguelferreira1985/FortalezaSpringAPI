package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getAllCategories());
    }
}
