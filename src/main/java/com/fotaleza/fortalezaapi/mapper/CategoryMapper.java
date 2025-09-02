package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.CategoryDTO;
import com.fotaleza.fortalezaapi.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
    void updateEntityFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
    List<CategoryDTO> toDTOList(List<Category> categories);

}
