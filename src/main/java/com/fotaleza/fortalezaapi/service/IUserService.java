package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.User;

import java.util.Set;

public interface IUserService {

    User createUser(String username, String password, Set<String> strRoles);
    User getByUserName(String userName);

}
