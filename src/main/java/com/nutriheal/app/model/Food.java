package com.nutriheal.app.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "foods")
public class Food {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    private String category; // vegetables, fruits, grains, proteins, etc.
    
    // Nutritional information
    private Double calories; // per 100g
    private Double protein;  // grams per 100g
    private Double carbohydrates; // grams per 100g
    private Double fat; // grams per 100g
    private Double fiber; // grams per 100g
    
    // Vitamins (mg per 100g)
    private Double vitaminA;
    private Double vitaminC;
    private Double vitaminD;
    private Double vitaminE;
    private Double vitaminK;
    
    // Minerals (mg per 100g)
    private Double sugar;           
    private Double omega3; 
    private Double iron;
    private Double calcium;
    private Double magnesium;
    private Double potassium;
    private Double zinc;
    
    @Column(length = 2000)
    private String healthBenefits; // Text describing health benefits
    
    @Column(length = 1000)
    private String preparationTips; // How to prepare this food
    
    // Many-to-many relationship with symptoms this food helps with
    @ManyToMany
    @JoinTable(
        name = "symptom_food_recommendations",
        joinColumns = @JoinColumn(name = "food_id"),
        inverseJoinColumns = @JoinColumn(name = "symptom_id")
    )
    @JsonIgnore
    private List<Symptom> helpsWith;
    
    // Constructors
    public Food() {}
    
    public Food(String name, String category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Double getCalories() { return calories; }
    public void setCalories(Double calories) { this.calories = calories; }
    
    public Double getProtein() { return protein; }
    public void setProtein(Double protein) { this.protein = protein; }
    
    public Double getCarbohydrates() { return carbohydrates; }
    public void setCarbohydrates(Double carbohydrates) { this.carbohydrates = carbohydrates; }
    
    public Double getFat() { return fat; }
    public void setFat(Double fat) { this.fat = fat; }
    
    public Double getFiber() { return fiber; }
    public void setFiber(Double fiber) { this.fiber = fiber; }
    
    public Double getVitaminA() { return vitaminA; }
    public void setVitaminA(Double vitaminA) { this.vitaminA = vitaminA; }
    
    public Double getVitaminC() { return vitaminC; }
    public void setVitaminC(Double vitaminC) { this.vitaminC = vitaminC; }
    
    public Double getVitaminD() { return vitaminD; }
    public void setVitaminD(Double vitaminD) { this.vitaminD = vitaminD; }
    
    public Double getVitaminE() { return vitaminE; }
    public void setVitaminE(Double vitaminE) { this.vitaminE = vitaminE; }
    
    public Double getVitaminK() { return vitaminK; }
    public void setVitaminK(Double vitaminK) { this.vitaminK = vitaminK; }
    
    public Double getSugar() { return sugar; }
    public void setSugar(Double sugar) { this.sugar = sugar; }
    
    public Double getOmega3() { return omega3; }
    public void setOmega3(Double omega3) { this.omega3 = omega3; }
    
    public Double getIron() { return iron; }
    public void setIron(Double iron) { this.iron = iron; }
    
    public Double getCalcium() { return calcium; }
    public void setCalcium(Double calcium) { this.calcium = calcium; }
    
    public Double getMagnesium() { return magnesium; }
    public void setMagnesium(Double magnesium) { this.magnesium = magnesium; }
    
    public Double getPotassium() { return potassium; }
    public void setPotassium(Double potassium) { this.potassium = potassium; }
    
    public Double getZinc() { return zinc; }
    public void setZinc(Double zinc) { this.zinc = zinc; }
    
    public String getHealthBenefits() { return healthBenefits; }
    public void setHealthBenefits(String healthBenefits) { this.healthBenefits = healthBenefits; }
    
    public String getPreparationTips() { return preparationTips; }
    public void setPreparationTips(String preparationTips) { this.preparationTips = preparationTips; }
    
    public List<Symptom> getHelpsWith() { return helpsWith; }
    public void setHelpsWith(List<Symptom> helpsWith) { this.helpsWith = helpsWith; }
}