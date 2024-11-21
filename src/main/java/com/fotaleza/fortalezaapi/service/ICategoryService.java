package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    Category saveCategory(Category category);
    List<Category> getAllCategorires();
    Category getCategoryById(Integer categoryId);
    Optional<Category> getCategoryByName(String categoryName);
    Boolean existsByCategoryrName(String categoryName);
    Category updateCategory(Category category);
    void deleteCategory(Integer categoryId);

}
