package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.repository.SubcategoryRepository;
import com.fotaleza.fortalezaapi.service.ISubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubcategoryServiceImpl implements ISubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    @Override
    public Subcategory createSubcategory(Subcategory subcategory) {
        if (subcategoryRepository.existsByName(subcategory.getName())) {
            throw new IllegalArgumentException("La Subcategoria ya existe.");
        }
        return subcategoryRepository.save(subcategory);
    }

    @Override
    public Subcategory updateSubcategory(Integer subcategoryId, Subcategory subcategory) {
        Subcategory subcategoryToUpdate = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new IllegalArgumentException("La Subcategoria no existe."));

        subcategoryToUpdate.setName(subcategory.getName());
        subcategoryToUpdate.setDescription(subcategory.getDescription());
        subcategoryToUpdate.setCategory(subcategory.getCategory());
        subcategoryToUpdate.setProducts(subcategory.getProducts());
        subcategoryToUpdate.setIsActivate(subcategory.getIsActivate());

        return subcategoryRepository.save(subcategory);
    }

    @Override
    public void deleteSubcategory(Integer subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new IllegalArgumentException("La Subcategoria no existe."));
        subcategory.setIsActivate(false);
        subcategoryRepository.save(subcategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Subcategory> getSubcategoryById(Integer subcategoryId) { return subcategoryRepository.findById(subcategoryId); }

    @Override
    @Transactional(readOnly = true)
    public List<Subcategory> getAllSubcategories( ) { return subcategoryRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public List<Subcategory> getActiveSubcategories() {
        return subcategoryRepository.findAll()
                .stream()
                .filter(Subcategory::getIsActivate)
                .toList();
    }
}
