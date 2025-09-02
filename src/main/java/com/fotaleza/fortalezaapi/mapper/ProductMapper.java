package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.ProductDTO;
import com.fotaleza.fortalezaapi.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "subcategoryId", source = "subcategory.id")
    @Mapping(target = "supplierIds", expression = "java(product.getSuppliers() != null ? product.getSuppliers().stream().map(s -> s.getId()).collect(java.util.stream.Collectors.toSet()) : java.util.Collections.emptySet())")    ProductDTO toDTO(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "suppliers", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    @Mapping(target = "suppliers", ignore = true)
    void updateEntityFromDTO(ProductDTO productDTO, @MappingTarget Product product);

    List<ProductDTO> toDTOList(List<Product> products);

}
