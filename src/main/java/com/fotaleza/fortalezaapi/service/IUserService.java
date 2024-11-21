package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(Integer userId);
    Optional<User> getByUserName(String userName);
    Boolean existsByUserName(String userName);
    User updateUser(User user);
    void deleteUser(Integer userId);

}
