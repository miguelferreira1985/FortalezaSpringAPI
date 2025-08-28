package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Subcategory;

import java.util.List;
import java.util.Optional;

public interface ISubcategoryService {

    Subcategory createSubcategory(Subcategory subcategory);
    Subcategory updateSubcategory(Integer subcategoryId, Subcategory subcategory);
    void deleteSubcategory(Integer subcategoryId);
    Optional<Subcategory> getSubcategoryById(Integer subcategoryId);
    List<Subcategory> getAllSubcategories();
    List<Subcategory> getActiveSubcategories();

}
