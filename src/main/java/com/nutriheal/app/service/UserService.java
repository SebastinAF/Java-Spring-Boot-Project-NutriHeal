package com.nutriheal.app.service;

import com.nutriheal.app.model.User;
import com.nutriheal.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Save a new user
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    // Get user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // Delete user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    // Check if username exists
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}