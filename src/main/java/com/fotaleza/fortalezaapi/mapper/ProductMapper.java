package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.ProductResponseDTO;
import com.fotaleza.fortalezaapi.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "subcategoryId", source = "subcategory.id")
    @Mapping(target = "supplierIds", expression = "java(product.getSuppliers() != null ? product.getSuppliers().stream().map(s -> s.getId()).collect(java.util.stream.Collectors.toSet()) : java.util.Collections.emptySet())")
    @Mapping(target = "profitMargin", expression = "java(product.getProfitMargin())")
    @Mapping(target = "profitValue", expression = "java(product.getProfitValue())")
    @Mapping(target = "inventoryValue", expression = "java(product.getInventoryValue())")
    @Mapping(target = "isBelowOrEqualMinimumStock", expression = "java(product.isBelowOrEqualMinimumStock())")
    ProductResponseDTO toResponseDTO(Product product);

    List<ProductResponseDTO> toResponseDTOList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "suppliers", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Product toEntity(ProductRequestDTO productRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "suppliers", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromRequestDTO(ProductRequestDTO productRequestDTO, @MappingTarget Product product);

}
