package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Subcategory;

import java.util.List;

public interface ISubcategoryService {

    Subcategory saveSubcategory(Subcategory subcategory);
    List<Subcategory> getAllActiveSubcategories();
    List<Subcategory> getAllInactivateSubcategories();
    Subcategory getSubcategoryById(Integer subcagoryId);
    Subcategory getSubcategoryByName(String subcategoryName);
    Boolean existsBySubcategoryName(String subcategoryName);
    Subcategory updateSubcategory(Subcategory subcategory);
    void deleteSubcategory(Integer subcategoryId);
}
