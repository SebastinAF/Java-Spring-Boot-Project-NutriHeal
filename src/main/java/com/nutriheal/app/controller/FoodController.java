package com.nutriheal.app.controller;

import com.nutriheal.app.model.Food;
import com.nutriheal.app.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/foods")
@CrossOrigin(origins = "*")
public class FoodController {

    @Autowired
    private FoodService foodService;

    // GET all foods - http://localhost:8080/api/foods
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // GET food by ID - http://localhost:8080/api/foods/1
    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Optional<Food> food = foodService.getFoodById(id);
        if (food.isPresent()) {
            return new ResponseEntity<>(food.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POST create new food - http://localhost:8080/api/foods
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody Food food) {
        if (foodService.existsByName(food.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Food already exists
        }
        Food savedFood = foodService.saveFood(food);
        return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
    }

    // PUT update food - http://localhost:8080/api/foods/1
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food food) {
        Optional<Food> existingFood = foodService.getFoodById(id);
        if (existingFood.isPresent()) {
            food.setId(id);
            Food updatedFood = foodService.saveFood(food);
            return new ResponseEntity<>(updatedFood, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE food by ID - http://localhost:8080/api/foods/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        Optional<Food> food = foodService.getFoodById(id);
        if (food.isPresent()) {
            foodService.deleteFood(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET foods by category - http://localhost:8080/api/foods/category/vegetables
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Food>> getFoodsByCategory(@PathVariable String category) {
        List<Food> foods = foodService.getFoodsByCategory(category);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // GET search foods by name - http://localhost:8080/api/foods/search?name=apple
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoods(@RequestParam String name) {
        List<Food> foods = foodService.searchFoodsByName(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // GET foods by calorie range - http://localhost:8080/api/foods/calories?min=100&max=300
    @GetMapping("/calories")
    public ResponseEntity<List<Food>> getFoodsByCalorieRange(
            @RequestParam Double min, 
            @RequestParam Double max) {
        List<Food> foods = foodService.getFoodsByCalorieRange(min, max);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // GET foods for symptom - http://localhost:8080/api/foods/symptom/1
    @GetMapping("/symptom/{symptomId}")
    public ResponseEntity<List<Food>> getFoodsForSymptom(@PathVariable Long symptomId) {
        List<Food> foods = foodService.getFoodsForSymptom(symptomId);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // GET high protein foods - http://localhost:8080/api/foods/high-protein?min=20
    @GetMapping("/high-protein")
    public ResponseEntity<List<Food>> getHighProteinFoods(@RequestParam Double min) {
        List<Food> foods = foodService.getHighProteinFoods(min);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    // GET vitamin C rich foods - http://localhost:8080/api/foods/vitamin-c?min=50
    @GetMapping("/vitamin-c")
    public ResponseEntity<List<Food>> getVitaminCRichFoods(@RequestParam Double min) {
        List<Food> foods = foodService.getVitaminCRichFoods(min);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}