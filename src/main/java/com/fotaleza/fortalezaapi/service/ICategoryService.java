package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.CategoryRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CategoryResponseDTO;

import java.util.List;

public interface ICategoryService {

    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO getCategoryById(Integer categoryId);
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO categoryRequestDTO);
    void deleteCategory (Integer categoryId);

}
