package com.nutriheal.app.controller;

import com.nutriheal.app.model.MealPlan;
import com.nutriheal.app.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mealplans")
@CrossOrigin(origins = "*")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    // Save a new meal plan
    @PostMapping
    public ResponseEntity<MealPlan> saveMealPlan(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.parseLong(request.get("userId").toString());
            String planName = (String) request.get("planName");
            String planData = (String) request.get("planData");
            boolean isAIPowered = (boolean) request.getOrDefault("isAIPowered", false);
            String aiInsights = (String) request.getOrDefault("aiInsights", "");

            MealPlan savedPlan = mealPlanService.saveMealPlan(userId, planName, planData, isAIPowered, aiInsights);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPlan);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Get all meal plans for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealPlan>> getUserMealPlans(@PathVariable Long userId) {
        try {
            List<MealPlan> plans = mealPlanService.getUserMealPlans(userId);
            return ResponseEntity.ok(plans);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get a specific meal plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<MealPlan> getMealPlanById(@PathVariable Long id) {
        Optional<MealPlan> plan = mealPlanService.getMealPlanById(id);
        
        if (plan.isPresent()) {
            return ResponseEntity.ok(plan.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a meal plan
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable Long id) {
        try {
            mealPlanService.deleteMealPlan(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}