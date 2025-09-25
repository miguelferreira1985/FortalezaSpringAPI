package com.fotaleza.fortalezaapi.dto.request;

import com.fotaleza.fortalezaapi.dto.UserDataDTO;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

    private Integer id;

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

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "El teléfono debe tener entre 7 y 15 dígitos y opcionalmente iniciar con +")
    private String phone;

    @NotBlank(message = "El NSS no puede estar en blanco.")
    @Size(max = 20, message = "El NSS no puede exceder 20 caracteres.")
    private String ssn;

    @NotNull(message = "User data is required.")
    private UserDataDTO userData;

}
