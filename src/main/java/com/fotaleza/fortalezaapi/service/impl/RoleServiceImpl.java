package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.ERole;
import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.repository.RoleRepository;
import com.fotaleza.fortalezaapi.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(ERole roleName) {

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con el nombre: " + roleName));

        return role;
    }
}
