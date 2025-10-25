package com.fotaleza.fortalezaapi.dto.request;

import com.fotaleza.fortalezaapi.dto.response.SupplierCostResponseDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    private Integer id;

    @NotBlank(message = "El nombre del poducto es obligarorio.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String name;

    @NotBlank(message = "El código del producto es obligatorio.")
    @Size(max = 20, message = "El código no puede exceder los 20 caracteres.")
    private String code;

    private String description;

    @NotNull(message = "El precio es obligarotio.")
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0.")
    private BigDecimal price;

    @NotNull(message = "El costo es obligarotio.")
    @DecimalMin(value = "0.0", message = "El costo debe ser mayor o igual a 0.")
    private BigDecimal cost;

    @NotNull(message = "El stock es obligatorio.")
    @DecimalMin(value = "0.0", message = "El stock debe ser mayor o igual a 0.")
    private BigDecimal stock;

    @NotNull(message = "El stock mínimo es obligatorio.")
    @DecimalMin(value = "0.0", message = "El stock mínimo debe ser mayor o igual a 0.")
    private BigDecimal minimumStock;

    @NotNull(message = "El stock recomendado es obligarorio.")
    @DecimalMin(value = "0.0", message = "El stock recomendado debe ser mayor o igual a 0.")
    private BigDecimal recommendedStock;

    private Integer subcategoryId;
    private Integer presentationId;
    private Set<SupplierCostRequestDTO> supplierCosts;

}
