package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.ProductDTO;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.Subcategory;
import com.fotaleza.fortalezaapi.model.Supplier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO dto) {
        Product product = Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .cost(dto.getCost())
                .stock(dto.getStock())
                .minimumStock(dto.getMinimumStock())
                .isActivate(dto.getIsActivate())
                .build();

        if (dto.getSubcategoryId() != null) {
            Subcategory subcategory = new Subcategory();
            subcategory.setId(dto.getSubcategoryId());
            product.setSubcategory(subcategory);
        }

        if (dto.getSupplierIds() != null) {
            Set<Supplier> suppliers =  new HashSet<>();
            for (Integer supplierId : dto.getSupplierIds()){
                Supplier supplier = new Supplier();
                supplier.setId(supplierId);
                suppliers.add(supplier);
            }
            product.setSuppliers(suppliers);
        }
        return product;
    }

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCode(product.getCode());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCost(product.getCost());
        dto.setStock(product.getStock());
        dto.setMinimumStock(product.getMinimumStock());
        dto.setIsActivate(product.getIsActivate());

        if (product.getSubcategory() != null) {
            dto.setSubcategoryId(product.getSubcategory().getId());
        }

        if (product.getSuppliers() != null) {
            Set<Integer> suppliersId = product.getSuppliers().stream()
                    .map(Supplier::getId)
                    .collect(Collectors.toSet());

            dto.setSupplierIds(suppliersId);
        }

        return dto;
    }
}
