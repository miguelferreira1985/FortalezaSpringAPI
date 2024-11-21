package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.User;

import java.util.List;

public interface IUserService {

    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(long userId);
    User getByUserName(String userName);
    Boolean existsByUserName(String userName);
    User updateUser(User user);
    void deleteUser(long userId);

}
