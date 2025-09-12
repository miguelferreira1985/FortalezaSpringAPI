package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.SupplierRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.SupplierResponseDTO;
import com.fotaleza.fortalezaapi.model.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {

    @Mapping(target = "productIds", expression = "java(supplier.getProducts() != null ? supplier.getProducts().stream().map(p -> p.getId()).collect(java.util.stream.Collectors.toSet()) : java.util.Collections.emptySet())")
    SupplierResponseDTO toResponseDTO(Supplier supplier);

    List<SupplierResponseDTO> toResponseDTOList(List<Supplier> suppliers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Supplier toEntity(SupplierRequestDTO supplierRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromRequestDTO(SupplierRequestDTO supplierRequestDTO, @MappingTarget Supplier supplier);

}
