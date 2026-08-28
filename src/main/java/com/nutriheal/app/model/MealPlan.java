package com.nutriheal.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "meal_plans")
public class MealPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    	
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"mealPlans", "password"})  
    private User user;
    
    @Column(nullable = false)
    private String planName;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String planData; // JSON string of the meal plan
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private boolean isAIPowered;
    
    @Column(columnDefinition = "TEXT")
    private String aiInsights;
    
    // Constructors
    public MealPlan() {
        this.createdAt = LocalDateTime.now();
    }
    
    public MealPlan(User user, String planName, String planData, boolean isAIPowered, String aiInsights) {
        this.user = user;
        this.planName = planName;
        this.planData = planData;
        this.isAIPowered = isAIPowered;
        this.aiInsights = aiInsights;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getPlanName() {
        return planName;
    }
    
    public void setPlanName(String planName) {
        this.planName = planName;
    }
    
    public String getPlanData() {
        return planData;
    }
    
    public void setPlanData(String planData) {
        this.planData = planData;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isAIPowered() {
        return isAIPowered;
    }
    
    public void setAIPowered(boolean isAIPowered) {
        this.isAIPowered = isAIPowered;
    }
    
    public String getAiInsights() {
        return aiInsights;
    }
    
    public void setAiInsights(String aiInsights) {
        this.aiInsights = aiInsights;
    }
}