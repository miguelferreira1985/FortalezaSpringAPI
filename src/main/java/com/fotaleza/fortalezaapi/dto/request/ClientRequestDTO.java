package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

    private Integer id;

    @NotBlank(message = "El nombre del cliente no puede estar en blanco.")
    @Size(max = 50, message = "El nombre del cliente no puede exceder los 50 caracteres.")
    private String name;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "El teléfono debe tener entre 7 y 15 dígitos y opcionalmente iniciar con +")
    private String phone;

    @NotBlank(message = "El RFC no puede estar en blanco.")
    @Size(max = 20, message = "El RFC no puede exceder 20 caracteres.")
    private String rfc;

}
