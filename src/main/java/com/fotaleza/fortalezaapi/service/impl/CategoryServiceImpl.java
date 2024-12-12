package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.model.ECategory;
import com.fotaleza.fortalezaapi.repository.CategoryRepository;
import com.fotaleza.fortalezaapi.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryByName(ECategory categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con el nombre: " + categoryName));
    }

}
