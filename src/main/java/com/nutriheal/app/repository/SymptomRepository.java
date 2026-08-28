package com.nutriheal.app.repository;

import com.nutriheal.app.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByCategory(String category);
    Symptom findByNameIgnoreCase(String name);
    List<Symptom> findByNameContainingIgnoreCase(String name);
    List<Symptom> findBySeverity(String severity);
    boolean existsByName(String name);
}