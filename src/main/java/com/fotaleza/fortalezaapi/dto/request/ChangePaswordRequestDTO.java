package com.fotaleza.fortalezaapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePaswordRequestDTO {

    @NotBlank(message = "La nueva contraseña no puede estar vacia.")
    private String newPassword;
}
