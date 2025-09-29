package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.request.SaleItemRequestDto;
import com.fotaleza.fortalezaapi.dto.response.SaleItemResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.SaleRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.SaleResponseDTO;
import com.fotaleza.fortalezaapi.model.Sale;
import com.fotaleza.fortalezaapi.model.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Set;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductMapper.class})
public interface SaleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "saleItems", ignore = true)
    @Mapping(target = "cashStart", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Sale toSaleEntity(SaleRequestDTO saleRequestDTO);

    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "saleItems", source = "saleItems")
    SaleResponseDTO toResponseDTO(Sale sale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    SaleItem toSaleItemEntity(SaleItemRequestDto saleItemRequestDto);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    SaleItemResponseDTO toSaleItemResponseDTO(SaleItem saleItem);

    Set<SaleItem> toSaleItemEntities(Set<SaleItemRequestDto> saleItemRequestDtos);
    Set<SaleItemResponseDTO> toSaleItemResponseDTOSet(Set<SaleItem> saleItems);

}
