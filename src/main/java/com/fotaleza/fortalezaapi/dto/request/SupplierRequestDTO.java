package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequestDTO {

    private Integer id;

    @NotBlank(message = "El nombre no puede estar en blanco.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String name;

    @Size(max = 50, message = "El contacto no puede exceder los 50 caracteres.")
    private String contact;

    @Size(max = 100, message = "La direccion no puede exceder los 100 caracteres.")
    private String address;

    @Email(message = "El formato del correo electronico no es valido.")
    private String email;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "El teléfono del contacto debe tener entre 7 y 15 dígitos y opcionalmente iniciar con +")
    private String contactPhone;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "El teléfono de oficina debe tener entre 7 y 15 dígitos y opcionalmente iniciar con +")
    private String officePhone;

}
