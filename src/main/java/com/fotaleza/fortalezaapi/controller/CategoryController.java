package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories(@RequestParam("isActivate") boolean isActivate) {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

}
