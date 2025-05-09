package com.fotaleza.fortalezaapi.mapper;

import com.fotaleza.fortalezaapi.dto.SubcategoryDTO;
import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.model.Product;
import com.fotaleza.fortalezaapi.model.Subcategory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SubcategoryMapper {

    public Subcategory toEntity(SubcategoryDTO dto) {
        Subcategory subcategory = Subcategory.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        if (dto.getCategoryId() != null) {
            Category category = new Category();
            category.setId(dto.getCategoryId());
            subcategory.setCategory(category);
        }

        if (dto.getProductIds() != null) {
            Set<Product> products = new HashSet<>();
            for (Integer productId : dto.getProductIds()) {
                Product product = new Product();
                product.setId(productId);
                products.add(product);
            }

        }


        return subcategory;
    }

    public SubcategoryDTO toDto(Subcategory subcategory) {
        SubcategoryDTO dto = new SubcategoryDTO();
        dto.setId(subcategory.getId());
        dto.setName(subcategory.getName());
        dto.setDescription(subcategory.getDescription());

        if (subcategory.getCategory() != null) {
            dto.setCategoryId(subcategory.getCategory().getId());
        }

        if (subcategory.getProducts() != null) {
            Set<Integer> productsId = subcategory.getProducts()
                    .stream()
                    .map(Product::getId)
                    .collect(Collectors.toSet());

            dto.setProductIds(productsId);
        }

        return dto;
    }
}
