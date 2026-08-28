package com.nutriheal.app.service;

import com.nutriheal.app.model.MealPlan;
import com.nutriheal.app.model.User;
import com.nutriheal.app.repository.MealPlanRepository;
import com.nutriheal.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private UserRepository userRepository;

    public MealPlan saveMealPlan(Long userId, String planName, String planData, boolean isAIPowered, String aiInsights) {
        Optional<User> user = userRepository.findById(userId);
        
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        MealPlan mealPlan = new MealPlan(user.get(), planName, planData, isAIPowered, aiInsights);
        return mealPlanRepository.save(mealPlan);
    }

    public List<MealPlan> getUserMealPlans(Long userId) {
        return mealPlanRepository.findByUserId(userId);
    }

    public Optional<MealPlan> getMealPlanById(Long id) {
        return mealPlanRepository.findById(id);
    }

    public void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }
}