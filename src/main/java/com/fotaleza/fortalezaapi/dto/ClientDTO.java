package com.fotaleza.fortalezaapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Integer id;

    @Size(max = 50, message = "El nombre de la Compañia no puede exceder los 50 caracteres.")
    private String companyName;

    @NotBlank(message = "El nombre no puede estar en blanco.")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar en blanco.")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres.")
    private String lastName;

    @Size(max = 100, message = "La direccion no puede exceder los 100 caracteres.")
    private String address;

    @Email(message = "El formato del correo electronico no es valido.")
    private String email;

    private String phone;

    @NotBlank(message = "El RFC no puede estar en blanco.")
    @Size(max = 20, message = "El RFC no puede exceder 20 caracteres.")
    private String rfc;

    private Boolean isActivate;

}
