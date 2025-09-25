package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.response.SubcategoryResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.SubcategoryRequestDTO;
import com.fotaleza.fortalezaapi.model.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubcategoryMapper {

    @Mapping(target = "category", source = "category")
    SubcategoryResponseDTO toResponseDTO(Subcategory subcategory);

    List<SubcategoryResponseDTO> toResponseDTOList(List<Subcategory> subcategories);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Subcategory toEntity(SubcategoryRequestDTO subcategoryRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromRequestDTO(SubcategoryRequestDTO subcategoryRequestDTO, @MappingTarget Subcategory subcategory);
}
