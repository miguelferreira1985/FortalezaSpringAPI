package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.ColorRequestDto;
import com.fotaleza.fortalezaapi.dto.response.ColorResponseDto;
import com.fotaleza.fortalezaapi.model.Color;
import com.fotaleza.fortalezaapi.repository.ColorRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColorMapperDto {

    public static ColorResponseDto toModel(Color entity) {

        ColorResponseDto model = new ColorResponseDto();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setCreatedDateTime(entity.getCreatedDateTime());
        model.setUpdatedDateTime(entity.getUpdatedDateTime());
        model.setIsActivate(entity.getIsActivate());

        return model;
    }

    public static Color toEntity(ColorRequestDto model) {

        Color entity = new Color();
        entity.setId(model.getId());
        entity.setName(model.getName());

        return entity;
    }

    public static List<ColorResponseDto> toModelList(List<Color> entities) {

        List<ColorResponseDto> colorResponseDtoList = new ArrayList<>();

        entities.forEach(entity -> colorResponseDtoList.add(toModel(entity)));

        return colorResponseDtoList;
    }

}
