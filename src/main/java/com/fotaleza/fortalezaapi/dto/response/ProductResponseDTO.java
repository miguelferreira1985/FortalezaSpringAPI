package com.fotaleza.fortalezaapi.dto.response;

import com.fotaleza.fortalezaapi.model.Presentation;
import com.fotaleza.fortalezaapi.model.Subcategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Integer id;
    private String name;
    private String code;
    private String description;
    private BigDecimal price;
    private BigDecimal cost;
    private BigDecimal stock;
    private BigDecimal minimumStock;
    private BigDecimal recommendedStock;
    private Subcategory subcategory;
    private Presentation presentation;
    private List<SupplierCostResponseDTO> supplierCosts;
    private Boolean isActivate;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String createdBy;
    private String updatedBy;

    private BigDecimal profitMargin;
    private BigDecimal profitValue;
    private BigDecimal inventoryValue;
    private Boolean isBelowOrEqualMinimumStock;

}
