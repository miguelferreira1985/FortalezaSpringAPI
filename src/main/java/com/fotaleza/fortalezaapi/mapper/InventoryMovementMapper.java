package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.InventoryMovementDTO;
import com.fotaleza.fortalezaapi.model.InventoryMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMovementMapper {

    InventoryMovementDTO toDTO(InventoryMovement inventoryMovement);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productCode", source = "product.code")
    InventoryMovement toEntity(InventoryMovementDTO inventoryMovementDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productCode", source = "product.code")
    void updateEntityFromDTO(InventoryMovementDTO inventoryMovementDTO, @MappingTarget InventoryMovement inventoryMovement);

    List<InventoryMovementDTO> toDTOList(List<InventoryMovement> inventoryMovements);

}
