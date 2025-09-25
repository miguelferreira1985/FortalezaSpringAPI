package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.response.SubcategoryResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.SubcategoryRequestDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.SubcategoryMapper;
import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.repository.CategoryRepository;
import com.fotaleza.fortalezaapi.repository.SubcategoryRepository;
import com.fotaleza.fortalezaapi.service.ISubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements ISubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryMapper subcategoryMapper;

    @Override
    @Transactional
    public SubcategoryResponseDTO createSubcategory(SubcategoryRequestDTO subcategoryRequestDTO) {
        validateNameUnique(subcategoryRequestDTO.getName(), null);

        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryRequestDTO);

        Category category = categoryRepository.findById(subcategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("La categoria no existe."));
        subcategory.setCategory(category);

        Subcategory savedSubcategory = subcategoryRepository.save(subcategory);

        return subcategoryMapper.toResponseDTO(savedSubcategory);

    }

    @Override
    @Transactional
    public SubcategoryResponseDTO updateSubcategory(Integer subcategoryId, SubcategoryRequestDTO subcategoryRequestDTO) {
        Subcategory subcategoryToUpdate = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La subcategoria no existe."));

        validateNameUnique(subcategoryRequestDTO.getName(), subcategoryId);

        subcategoryMapper.updateEntityFromRequestDTO(subcategoryRequestDTO, subcategoryToUpdate);

        Category category = categoryRepository.findById(subcategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("La categoría seleccionada no existe."));
        subcategoryToUpdate.setCategory(category);

        Subcategory updatedSubcategory = subcategoryRepository.save(subcategoryToUpdate);
        return subcategoryMapper.toResponseDTO(updatedSubcategory);
    }

    @Override
    public void deleteSubcategory(Integer subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La subcategoria no existe."));

        subcategory.setIsActivate(false);
        subcategoryRepository.save(subcategory);
    }

    @Override
    public SubcategoryResponseDTO getSubcategoryById(Integer subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La subcategoria no existe."));

        return subcategoryMapper.toResponseDTO(subcategory);
    }

    @Override
    public List<SubcategoryResponseDTO> getAllSubcategories(Boolean isActivate) {
        List<Subcategory> subcategories = Optional.ofNullable(isActivate)
                .map(subcategoryRepository::findByIsActivate)
                .orElseGet(subcategoryRepository::findAll);

        return subcategoryMapper.toResponseDTOList(subcategories);
    }

    private void validateNameUnique(String name, Integer subcategoryId) {
        subcategoryRepository.findByName(name)
                .ifPresent(sc -> {
                    if (subcategoryId == null || !sc.getId().equals(subcategoryId)) {
                        throw new ResourceAlreadyExistsException("La subcategoria con el nombre ya existe.");
                    }
                });
    }
}
