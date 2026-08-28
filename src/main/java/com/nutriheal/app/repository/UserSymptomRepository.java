package com.nutriheal.app.repository;

import com.nutriheal.app.model.UserSymptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserSymptomRepository extends JpaRepository<UserSymptom, Long> {
    List<UserSymptom> findByUserId(Long userId);
    List<UserSymptom> findBySymptomId(Long symptomId);
    List<UserSymptom> findByUserIdAndIsActive(Long userId, Boolean isActive);
    
    @Query("SELECT us FROM UserSymptom us WHERE us.user.id = ?1 AND us.symptom.category = ?2")
    List<UserSymptom> findByUserIdAndSymptomCategory(Long userId, String category);
}