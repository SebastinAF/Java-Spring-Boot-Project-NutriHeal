package com.nutriheal.app.repository;

import com.nutriheal.app.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategory(String category);
    List<Food> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT f FROM Food f WHERE f.calories BETWEEN ?1 AND ?2")
    List<Food> findByCalorieRange(Double minCalories, Double maxCalories);
    
    // Find foods that help with specific symptoms
    @Query("SELECT f FROM Food f JOIN f.helpsWith s WHERE s.id = ?1")
    List<Food> findBySymptomId(Long symptomId);
    
    // Find high-protein foods
    @Query("SELECT f FROM Food f WHERE f.protein > ?1 ORDER BY f.protein DESC")
    List<Food> findHighProteinFoods(Double minProtein);
    
    // Find foods rich in specific vitamins
    @Query("SELECT f FROM Food f WHERE f.vitaminC > ?1")
    List<Food> findVitaminCRichFoods(Double minVitaminC);
}