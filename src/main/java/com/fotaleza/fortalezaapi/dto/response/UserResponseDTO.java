package com.fotaleza.fortalezaapi.dto.response;

import com.fotaleza.fortalezaapi.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private Boolean isBlocked;
    private Boolean isActivate;

    private Set<Role> roles;
}
