package com.nutriheal.app.service;

import com.nutriheal.app.model.Symptom;
import com.nutriheal.app.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SymptomService {

    @Autowired
    private SymptomRepository symptomRepository;

    // Save a new symptom
    public Symptom saveSymptom(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    // Get all symptoms
    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }

    // Get symptom by ID
    public Optional<Symptom> getSymptomById(Long id) {
        return symptomRepository.findById(id);
    }

    // Find symptoms by category
    public List<Symptom> getSymptomsByCategory(String category) {
        return symptomRepository.findByCategory(category);
    }

    // Find symptom by name
    public Symptom getSymptomByName(String name) {
        return symptomRepository.findByNameIgnoreCase(name);
    }

    // Search symptoms by name
    public List<Symptom> searchSymptomsByName(String name) {
        return symptomRepository.findByNameContainingIgnoreCase(name);
    }

    // Find symptoms by severity
    public List<Symptom> getSymptomsBySeverity(String severity) {
        return symptomRepository.findBySeverity(severity);
    }

    // Delete symptom
    public void deleteSymptom(Long id) {
        symptomRepository.deleteById(id);
    }

    // Check if symptom exists
    public boolean existsByName(String name) {
        return symptomRepository.existsByName(name);
    }
}