package com.nutriheal.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_symptoms")
public class UserSymptom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symptom_id", nullable = false)
    private Symptom symptom;
    
    private String severity; // mild, moderate, severe
    
    private LocalDateTime reportedDate;
    
    private LocalDateTime lastUpdated;
    
    @Column(length = 1000)
    private String notes; // User's additional notes about the symptom
    
    private Boolean isActive; // Whether the symptom is currently active
    
    // Constructors
    public UserSymptom() {
        this.reportedDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
        this.isActive = true;	
    }
    
    public UserSymptom(User user, Symptom symptom, String severity) {
        this();
        this.user = user;
        this.symptom = symptom;
        this.severity = severity;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Symptom getSymptom() { return symptom; }
    public void setSymptom(Symptom symptom) { this.symptom = symptom; }
    
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { 
        this.severity = severity;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public LocalDateTime getReportedDate() { return reportedDate; }
    public void setReportedDate(LocalDateTime reportedDate) { this.reportedDate = reportedDate; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { 
        this.notes = notes;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { 
        this.isActive = isActive;
        this.lastUpdated = LocalDateTime.now();
    }
}