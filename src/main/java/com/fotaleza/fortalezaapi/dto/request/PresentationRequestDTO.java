package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PresentationRequestDTO {

    private Integer id;

    @NotBlank(message = "El nombre de la presentación no puede estar en blanco.")
    @Size(max = 50, message = "El nombre de la presentación no puede exceder los 50 caracteres.")
    private String name;

    @NotBlank(message = "La abreviación de la presentación no puede estar en blanco.")
    @Size(max = 10, message = "La abreviación de la presentación no puede exceder los 10 caracteres.")
    private String abbreviation;

}
