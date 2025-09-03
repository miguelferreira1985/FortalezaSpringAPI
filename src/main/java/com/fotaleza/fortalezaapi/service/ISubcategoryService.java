package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.SubcategoryResponseDTO;
import com.fotaleza.fortalezaapi.dto.SubcategoryRequestDTO;

import java.util.List;

public interface ISubcategoryService {

    SubcategoryResponseDTO createSubcategory(SubcategoryRequestDTO subcategoryRequestDTO);
    SubcategoryResponseDTO updateSubcategory(Integer subcategoryId, SubcategoryRequestDTO subcategoryRequestDTO);
    void deleteSubcategory(Integer subcategoryId);
    SubcategoryResponseDTO getSubcategoryById(Integer subcategoryId);
    List<SubcategoryResponseDTO> getAllSubcategories(Boolean isActivate);

}
