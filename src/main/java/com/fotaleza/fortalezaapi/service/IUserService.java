package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.response.UserResponseDTO;
import com.fotaleza.fortalezaapi.model.User;

import java.util.List;
import java.util.Set;

public interface IUserService {

    User createUser(String username, String password, Set<String> strRoles);
    User getByUserName(String userName);
    UserResponseDTO activateUser(Long userId);
    UserResponseDTO deactivateUser(Long userId);
    UserResponseDTO unblockUser(Long userId);
    UserResponseDTO changePassowrd(Long userId, String newPassword);
    UserResponseDTO updateRoles(Long userId, Set<String> strRoles);
    void processFailedLogin(String username);
    void resetFailedAttempts(String username);
    List<UserResponseDTO> getAllUsers(Boolean isActivate);
    void deleteUserIfDeactivate(Long userId);
}
