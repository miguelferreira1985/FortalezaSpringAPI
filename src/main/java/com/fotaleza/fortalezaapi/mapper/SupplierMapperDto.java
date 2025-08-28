package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.SupplierRequestDto;
import com.fotaleza.fortalezaapi.dto.response.SupplierResponseDto;
import com.fotaleza.fortalezaapi.model.Supplier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SupplierMapperDto {

    public static SupplierResponseDto toModel(Supplier entity) {

        SupplierResponseDto model = new SupplierResponseDto();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setContact(entity.getContact());
        model.setEmail(entity.getEmail());
        model.setAddress(entity.getAddress());
        model.setPhone(entity.getPhone());
        model.setCreatedDateTime(entity.getCreatedDateTime());
        model.setUpdatedDateTime(entity.getUpdatedDateTime());
        model.setIsActivate(entity.getIsActivate());

        return model;
    }

    public static Supplier toEntity(SupplierRequestDto model) {

        Supplier entity = new Supplier();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setContact(model.getContact());
        entity.setAddress(model.getAddress());
        entity.setEmail(model.getEmail());
        entity.setPhone(model.getPhone());

        return entity;
    }

    public static List<SupplierResponseDto> toModelListResponse(List<Supplier> entities) {

        List<SupplierResponseDto> supplierResponseDtoList = new ArrayList<>();

        entities.forEach(entity -> supplierResponseDtoList.add(toModel(entity)));

        return supplierResponseDtoList;
    }

}
