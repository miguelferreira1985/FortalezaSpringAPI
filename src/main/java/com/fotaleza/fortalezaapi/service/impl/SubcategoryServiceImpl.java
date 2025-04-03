package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.repository.SubcategoryRepository;
import com.fotaleza.fortalezaapi.service.ISubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryServiceImpl implements ISubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public Subcategory saveSubcategory(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    @Override
    public List<Subcategory> getAllSubcategories(Boolean isActivate) {
        return subcategoryRepository
                .findAll()
                .stream()
                .filter(subcategory -> subcategory.getIsActivate().equals(isActivate))
                .toList();
    }

    @Override
    public Subcategory getSubcategoryById(Integer subcagoryId) {
        return subcategoryRepository.findById(subcagoryId).orElse(null);
    }

    @Override
    public Subcategory getSubcategoryByName(String subcategoryName) {
        return subcategoryRepository.findByName(subcategoryName)
                .orElseThrow(() -> new RuntimeException(String.format("Subactegoria no encontrada con el nombre: %s", subcategoryName)));
    }

    @Override
    public Boolean existsBySubcategoryName(String subcategoryName) {
        return subcategoryRepository.existsByName(subcategoryName);
    }

    @Override
    public Subcategory updateSubcategory(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    @Override
    public void deleteSubcategory(Integer subcategoryId) {
        subcategoryRepository.deleteById(subcategoryId);
    }
}
