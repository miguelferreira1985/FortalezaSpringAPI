package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.response.SupplierCostResponseDTO;
import com.fotaleza.fortalezaapi.dto.request.ProductRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ProductResponseDTO;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.SupplierProduct;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {


    @Mapping(target = "subcategory", source = "subcategory")
    @Mapping(target = "presentation", source = "presentation")
    @Mapping(target = "supplierCosts", source = "supplierProducts")
    @Mapping(target = "profitMargin", expression = "java(product.getProfitMargin())")
    @Mapping(target = "profitValue", expression = "java(product.getProfitValue())")
    @Mapping(target = "inventoryValue", expression = "java(product.getInventoryValue())")
    @Mapping(target = "isBelowOrEqualMinimumStock", expression = "java(product.isBelowOrEqualMinimumStock())")
    ProductResponseDTO toResponseDTO(Product product);

    List<ProductResponseDTO> toResponseDTOList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "presentation", ignore = true)
    @Mapping(target = "supplierProducts", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Product toEntity(ProductRequestDTO productRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "presentation", ignore = true)
    @Mapping(target = "supplierProducts", ignore = true)
    @Mapping(target = "isActivate", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "updatedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromRequestDTO(ProductRequestDTO productRequestDTO, @MappingTarget Product product);

    default List<SupplierCostResponseDTO> map(List<SupplierProduct> supplierProducts) {
        if (supplierProducts == null || supplierProducts.isEmpty()) return java.util.Collections.emptyList();

        return supplierProducts.stream()
                .map(sp -> SupplierCostResponseDTO.builder()
                        .supplierId(sp.getSupplier() != null ? sp.getSupplier().getId() : null)
                        .supplierName(sp.getSupplier() != null ? sp.getSupplier().getName() : null)
                        .cost(sp.getCost())
                        .discount(sp.getDiscount())
                        .build())
                .collect(Collectors.toList());
    }

}
