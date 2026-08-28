package com.nutriheal.app.service;

import com.nutriheal.app.model.Food;
import com.nutriheal.app.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    // Save a new food
    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }

    // Get all foods
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // Get food by ID
    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }

    // Find foods by category
    public List<Food> getFoodsByCategory(String category) {
        return foodRepository.findByCategory(category);
    }

    // Search foods by name
    public List<Food> searchFoodsByName(String name) {
        return foodRepository.findByNameContainingIgnoreCase(name);
    }

    // Find foods by calorie range
    public List<Food> getFoodsByCalorieRange(Double minCalories, Double maxCalories) {
        return foodRepository.findByCalorieRange(minCalories, maxCalories);
    }

    // Find foods that help with specific symptom
    public List<Food> getFoodsForSymptom(Long symptomId) {
        return foodRepository.findBySymptomId(symptomId);
    }

    // Find high protein foods
    public List<Food> getHighProteinFoods(Double minProtein) {
        return foodRepository.findHighProteinFoods(minProtein);
    }

    // Find vitamin C rich foods
    public List<Food> getVitaminCRichFoods(Double minVitaminC) {
        return foodRepository.findVitaminCRichFoods(minVitaminC);
    }

    // Delete food
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }

    // Check if food exists by name
    public boolean existsByName(String name) {
        List<Food> foods = foodRepository.findByNameContainingIgnoreCase(name);
        return !foods.isEmpty();
    }
}