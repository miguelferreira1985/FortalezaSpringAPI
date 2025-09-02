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

    SupplierDTO toDTO(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    Supplier toEntity(SupplierDTO supplierDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(SupplierDTO supplierDTO, @MappingTarget Supplier supplier);

    List<SupplierDTO> toDTOList(List<Supplier> suppliers);

}
