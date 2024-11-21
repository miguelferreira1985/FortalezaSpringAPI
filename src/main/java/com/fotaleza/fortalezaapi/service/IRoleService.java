package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Role;

import java.util.Optional;

public interface IRoleService {

    Optional<Role> getRoleByName(String roleName);

}
