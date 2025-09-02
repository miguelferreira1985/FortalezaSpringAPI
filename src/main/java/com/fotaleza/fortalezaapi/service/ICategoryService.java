package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.CategoryDTO;
import com.fotaleza.fortalezaapi.model.Category;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Integer categoryId);
}
