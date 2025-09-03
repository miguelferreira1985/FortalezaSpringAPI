package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.SubcategoryResponseDTO;
import com.fotaleza.fortalezaapi.dto.SubcategoryRequestDTO;
import com.fotaleza.fortalezaapi.model.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import java.util.stream.Collectors;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubcategoryMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "productIds", expression = "java(subcategory.getProducts() != null ? subcategory.getProducts().stream().map(p -> p.getId()).collect(java.util.stream.Collectors.toSet()) : java.util.Collections.emptySet())")
    SubcategoryResponseDTO toResponseDTO(Subcategory subcategory);

    List<SubcategoryResponseDTO> toResponseDTOList(List<Subcategory> subcategories);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "category.id", source = "categoryId")
    Subcategory toEntity(SubcategoryRequestDTO subcategoryRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "category.id", source = "categoryId")
    void updateEntityFromRequestDTO(SubcategoryRequestDTO subcategoryRequestDTO, @MappingTarget Subcategory subcategory);
}
