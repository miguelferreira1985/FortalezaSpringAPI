package com.fotaleza.fortalezaapi.dto.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRolesRequestDTO {

    @NotEmpty(message = "La lista de roles no puede estar vacia.")
    private Set<String> roles;

}
