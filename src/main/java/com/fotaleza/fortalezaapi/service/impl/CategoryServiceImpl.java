package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.request.CategoryRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CategoryResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.CategoryMapper;
import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.repository.CategoryRepository;
import com.fotaleza.fortalezaapi.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {

        validateNameUnique(categoryRequestDTO.getName(), null);

        Category category = categoryMapper.toEntity(categoryRequestDTO);
        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponseDTO(savedCategory);
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category categoryToUpdate = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría que desea actualiza no existe."));

        validateNameUnique(categoryRequestDTO.getName(), categoryId);

        categoryMapper.updateEntityFromRequestDTO(categoryRequestDTO, categoryToUpdate);

        Category updatedCategory = categoryRepository.save(categoryToUpdate);

        return categoryMapper.toResponseDTO(updatedCategory);

    }

    @Override
    @Transactional
    public void deleteCategory (Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría no existe"));

        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría no existe"));
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toResponseDTOList(categories);
    }

    private void validateNameUnique(String name, Integer categoryId) {

       final String errorMessage = String.format("Ya existe una categoría con el nombre %s.", name.toUpperCase());

        if (categoryId == null) {
            categoryRepository.findByName(name)
                    .ifPresent(c -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        } else {
            categoryRepository.findByNameAndIdNot(name, categoryId)
                    .ifPresent(c -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        }
    }

}
