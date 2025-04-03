package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.SubcategoryRequestDto;
import com.fotaleza.fortalezaapi.dto.response.SubCategoryResponseDto;
import com.fotaleza.fortalezaapi.model.Subcategory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubcategoryMapperDto {

    public static SubCategoryResponseDto toModel(Subcategory entity) {

        SubCategoryResponseDto model = new SubCategoryResponseDto();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setCategory(entity.getCategory());
        model.setCreatedDateTime(entity.getCreatedDateTime());
        model.setUpdatedDateTime(entity.getUpdatedDateTime());
        model.setIsActivate(entity.getIsActivate());

        return model;
    }

    public static Subcategory toEntity(SubcategoryRequestDto model) {

        Subcategory entity = new Subcategory();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setCategory(model.getCategory());

        return entity;
    }

    public static List<SubCategoryResponseDto> toModelListResponse(List<Subcategory> entities) {

        List<SubCategoryResponseDto> subCategoryResponseDtoList = new ArrayList<>();

        entities.forEach(entity -> subCategoryResponseDtoList.add(toModel(entity)));

        return subCategoryResponseDtoList;

    }

}
