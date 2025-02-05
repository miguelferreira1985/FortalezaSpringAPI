package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.ERole;
import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.repository.RoleRepository;
import com.fotaleza.fortalezaapi.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(ERole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con el nombre: " + roleName));
    }
}
