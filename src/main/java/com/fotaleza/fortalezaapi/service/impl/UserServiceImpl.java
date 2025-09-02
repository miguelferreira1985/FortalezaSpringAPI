package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.exception.UserAlreadyExistsException;
import com.fotaleza.fortalezaapi.model.ERole;
import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.repository.RoleRepository;
import com.fotaleza.fortalezaapi.repository.UserRepository;
import com.fotaleza.fortalezaapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder encoder;

    @Override
    public User createUser(String userName, String password, Set<String> strRoles) {
        if (userRepository.existsByUsername(userName)) {
            throw new UserAlreadyExistsException("El usario ya existe.");
        }

        User user = new User();
        user.setUsername(userName);
        user.setPassword(encoder.encode(password));

        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role cashierRole = roleService.getRoleByName(ERole.ROLE_CASHIER);
            roles.add(cashierRole);
        } else {

            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.getRoleByName(ERole.ROLE_ADMIN);
                        roles.add(adminRole);
                        break;
                    case "manager":
                        Role managerRole = roleService.getRoleByName(ERole.ROLE_MANAGER);
                        roles.add(managerRole);
                        break;
                    default:
                        Role cashierRole = roleService.getRoleByName(ERole.ROLE_CASHIER);
                        roles.add(cashierRole);
                        break;
                }
            });
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + userName));
    }

    @Override
    public Boolean existsByUserName(String userName) {
        return userRepository.existsByUsername(userName);
    }

}
