package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.User;
import com.fotaleza.fortalezaapi.repository.UserRepository;
import com.fotaleza.fortalezaapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) { return userRepository.save(user); }

    @Override
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + userName));
    }

    @Override
    public Boolean existsByUserName(String userName) { return userRepository.existsByUsername(userName); }

    @Override
    public User updateUser(User user) { return userRepository.save(user); }

    @Override
    public void deleteUser(long userId) { userRepository.deleteById(userId); }
}
