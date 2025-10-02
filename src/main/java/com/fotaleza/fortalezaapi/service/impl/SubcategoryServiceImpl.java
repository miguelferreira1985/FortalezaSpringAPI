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
                .orElseThrow(() -> new ResourceNotFoundException("La categoría que intenta asignar no existe."));

        subcategory.setCategory(category);

        Subcategory savedSubcategory = subcategoryRepository.save(subcategory);

        return subcategoryMapper.toResponseDTO(savedSubcategory);

    }

    @Override
    @Transactional
    public SubcategoryResponseDTO updateSubcategory(Integer subcategoryId, SubcategoryRequestDTO subcategoryRequestDTO) {
        Subcategory subcategoryToUpdate = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La subcategoría que desea actualizar no existe."));

        validateNameUnique(subcategoryRequestDTO.getName(), subcategoryId);

        subcategoryMapper.updateEntityFromRequestDTO(subcategoryRequestDTO, subcategoryToUpdate);

        Category category = categoryRepository.findById(subcategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("La categoría que desea asignar no existe."));
        subcategoryToUpdate.setCategory(category);

        Subcategory updatedSubcategory = subcategoryRepository.save(subcategoryToUpdate);
        return subcategoryMapper.toResponseDTO(updatedSubcategory);
    }

    @Override
    public void deleteSubcategory(Integer subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La subcategoría no existe."));

        subcategoryRepository.deleteById(subcategoryId);
    }

    @Override
    public SubcategoryResponseDTO getSubcategoryById(Integer subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("La subcategoría no existe."));

        return subcategoryMapper.toResponseDTO(subcategory);
    }

    @Override
    public List<SubcategoryResponseDTO> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll()
                .stream().toList();

        return subcategoryMapper.toResponseDTOList(subcategories);
    }

    private void validateNameUnique(String name, Integer subcategoryId) {

        final String errorMessage = String.format("Ya existe una subcategoría con el nombre %s.", name.toUpperCase());

        if (subcategoryId == null) {
            subcategoryRepository.findByName(name)
                    .ifPresent(sc -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        } else {
            subcategoryRepository.findByNameAndNotId(name, subcategoryId)
                    .ifPresent(sc -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        }

    }
}
