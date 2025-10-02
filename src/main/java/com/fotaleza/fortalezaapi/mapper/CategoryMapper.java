package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.CategoryRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.CategoryResponseDTO;
import com.fotaleza.fortalezaapi.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    CategoryResponseDTO toResponseDTO(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Category toEntity(CategoryRequestDTO categoryRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromRequestDTO(CategoryRequestDTO categoryRequestDTO, @MappingTarget Category category);

    List<CategoryResponseDTO> toResponseDTOList(List<Category> categories);

}
