package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.response.UserResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.enums.ERole;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.UserMapper;
import com.fotaleza.fortalezaapi.model.Role;
import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.repository.UserRepository;
import com.fotaleza.fortalezaapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private static final int MAX_FAILED_ATTEMPTS = 5;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public User createUser(String userName, String password, Set<String> strRoles) {
        if (userRepository.existsByUsername(userName)) {
            throw new ResourceAlreadyExistsException("El usario ya existe.");
        }

        User user = new User();
        user.setUsername(userName);
        user.setPassword(encoder.encode(password));

        Set<Role> roles = addRoles(strRoles);

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuario no encontrado con el nombre: %s", userName)));
    }

    @Override
    @Transactional
    public UserResponseDTO activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
        user.setIsActivate(true);

        User activateUser = userRepository.save(user);

        return userMapper.toResponseDTO(activateUser);
    }

    @Override
    @Transactional
    public UserResponseDTO deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
        user.setIsActivate(false);

        User deactivateUser = userRepository.save(user);

        return userMapper.toResponseDTO(deactivateUser);
    }

    @Override
    public UserResponseDTO unblockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
        user.setIsBlocked(false);
        user.setFailedAttempts(0);

        User unblockedUser = userRepository.save(user);

        return userMapper.toResponseDTO(unblockedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO changePassowrd(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
        user.setPassword(encoder.encode(newPassword));

        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO updateRoles(Long userId, Set<String> strRoles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

        Set<Role> roles = addRoles(strRoles);

        user.setRoles(roles);
        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public void processFailedLogin(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    int newFails = user.getFailedAttempts() + 1;
                    user.setFailedAttempts(newFails);

                    if ( newFails >= MAX_FAILED_ATTEMPTS) {
                        user.setIsBlocked(true);
                    }

                    userRepository.save(user);
                });
    }

    @Override
    @Transactional
    public void resetFailedAttempts(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    user.setFailedAttempts(0);
                    userRepository.save(user);
                });
    }

    @Override
    public List<UserResponseDTO> getAllUsers(Boolean isActivate) {
        List<User> users = Optional.ofNullable(isActivate)
                .map(userRepository::findByIsActivate)
                .orElseGet(userRepository::findAll);

        return userMapper.toResponseDTOList(users);
    }

    private Set<Role> addRoles(Set<String> strRoles) {

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

        return roles;
    }
}
