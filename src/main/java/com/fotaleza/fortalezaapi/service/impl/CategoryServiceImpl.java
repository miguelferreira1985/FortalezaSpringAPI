package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.repository.CategoryRepository;
import com.fotaleza.fortalezaapi.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) { return categoryRepository.save(category); }

    @Override
    public List<Category> getAllCategorires() { return categoryRepository.findAll(); }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElse(null);
    }

    @Override
    public Optional<Category> getCategoryByName(String categoryName) {

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con el nombre: " + categoryName));

        return Optional.ofNullable(category);
    }

    @Override
    public Boolean existsByCategoryrName(String categoryName) { return categoryRepository.existsByName(categoryName); }

    @Override
    public Category updateCategory(Category category) { return categoryRepository.save(category); }

    @Override
    public void deleteCategory(Integer categoryId) { categoryRepository.deleteById(categoryId); }
}
