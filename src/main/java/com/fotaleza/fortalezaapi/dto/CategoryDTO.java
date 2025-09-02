package com.fotaleza.fortalezaapi.dto;

import com.fotaleza.fortalezaapi.model.ECategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacio.")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private ECategory name;
}
