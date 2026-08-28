package com.nutriheal.app.service;

import com.nutriheal.app.model.UserSymptom;
import com.nutriheal.app.repository.UserSymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserSymptomService {

    @Autowired
    private UserSymptomRepository userSymptomRepository;

    // Save user symptom
    public UserSymptom saveUserSymptom(UserSymptom userSymptom) {
        userSymptom.setLastUpdated(LocalDateTime.now());
        return userSymptomRepository.save(userSymptom);
    }

    // Get all user symptoms
    public List<UserSymptom> getAllUserSymptoms() {
        return userSymptomRepository.findAll();
    }

    // Get user symptom by ID
    public Optional<UserSymptom> getUserSymptomById(Long id) {
        return userSymptomRepository.findById(id);
    }

    // Get symptoms for a specific user
    public List<UserSymptom> getSymptomsByUserId(Long userId) {
        return userSymptomRepository.findByUserId(userId);
    }

    // Get active symptoms for a user
    public List<UserSymptom> getActiveSymptomsByUserId(Long userId) {
        return userSymptomRepository.findByUserIdAndIsActive(userId, true);
    }

    // Get users with a specific symptom
    public List<UserSymptom> getUsersBySymptomId(Long symptomId) {
        return userSymptomRepository.findBySymptomId(symptomId);
    }

    // Get symptoms by user and category
    public List<UserSymptom> getUserSymptomsByCategory(Long userId, String category) {
        return userSymptomRepository.findByUserIdAndSymptomCategory(userId, category);
    }

    // Deactivate a symptom for user
    public UserSymptom deactivateUserSymptom(Long userSymptomId) {
        Optional<UserSymptom> userSymptom = userSymptomRepository.findById(userSymptomId);
        if (userSymptom.isPresent()) {
            UserSymptom us = userSymptom.get();
            us.setIsActive(false);
            us.setLastUpdated(LocalDateTime.now());
            return userSymptomRepository.save(us);
        }
        return null;
    }

    // Delete user symptom
    public void deleteUserSymptom(Long id) {
        userSymptomRepository.deleteById(id);
    }
}