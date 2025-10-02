package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryRequestDTO {

    private Integer id;

    @NotBlank(message = "El nombre de la subcategoría no puede estar en blanco.")
    @Size(max = 50, message = "El nombre de la subcstegoría no puede exceder los 50 caracteres.")
    private String name;

    private String description;
    private Integer categoryId;

}
