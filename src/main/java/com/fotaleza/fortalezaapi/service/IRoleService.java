package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.enums.ERole;
import com.fotaleza.fortalezaapi.model.Role;

public interface IRoleService {

    Role getRoleByName(ERole roleName);

}
