package com.nutriheal.app.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "symptoms")
public class Symptom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    private String category; // digestive, cardiovascular, mental_health, etc.
    
    private String severity; // mild, moderate, severe
    
    @Column(length = 2000)
    private String nutritionalGuidance; // General nutrition advice for this symptom
    
    // Many-to-many relationship with foods that help with this symptom
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "symptom_food_recommendations",
        joinColumns = @JoinColumn(name = "symptom_id"),
        inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    @JsonIgnore
    private List<Food> recommendedFoods;
    
    
    // Constructors
    public Symptom() {}
    
    public Symptom(String name, String category, String description) {
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
    
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    
    public String getNutritionalGuidance() { return nutritionalGuidance; }
    public void setNutritionalGuidance(String nutritionalGuidance) { 
        this.nutritionalGuidance = nutritionalGuidance; 
    }
    
    public List<Food> getRecommendedFoods() { return recommendedFoods; }
    public void setRecommendedFoods(List<Food> recommendedFoods) { 
        this.recommendedFoods = recommendedFoods; 
    }
    
//    public List<UserSymptom> getUserSymptoms() { return userSymptoms; }
//    public void setUserSymptoms(List<UserSymptom> userSymptoms) { 
//        this.userSymptoms = userSymptoms; 
//    }
}