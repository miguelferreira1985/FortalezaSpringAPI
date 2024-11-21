package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Category;

import java.util.List;

public interface ICategoryService {

    Category saveCategory(Category category);
    List<Category> getAllCategorires();
    Category getCategoryById(Integer categoryId);
    Category getCategoryByName(String categoryName);
    Boolean existsByCategoryrName(String categoryName);
    Category updateCategory(Category category);
    void deleteCategory(Integer categoryId);

}
