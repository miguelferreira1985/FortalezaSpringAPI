package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.SupplierDTO;
import com.fotaleza.fortalezaapi.model.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {

    @Mapping(target = "productIds", expression = "java(supplier.getProducts() != null ? supplier.getProducts().stream().map(p -> p.getId()).collect(java.util.stream.Collectors.toSet()) : java.util.Collections.emptySet())")
    SupplierDTO toDTO(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    Supplier toEntity(SupplierDTO supplierDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    void updateEntityFromDTO(SupplierDTO supplierDTO, @MappingTarget Supplier supplier);

    List<SupplierDTO> toDTOList(List<Supplier> suppliers);

}
