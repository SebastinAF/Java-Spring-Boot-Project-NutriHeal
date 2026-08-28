package com.nutriheal.app.controller;

import com.nutriheal.app.model.UserSymptom;
import com.nutriheal.app.service.UserSymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-symptoms")
@CrossOrigin(origins = "*")
public class UserSymptomController {

    @Autowired
    private UserSymptomService userSymptomService;

    // GET all user symptoms - http://localhost:8080/api/user-symptoms
    @GetMapping
    public ResponseEntity<List<UserSymptom>> getAllUserSymptoms() {
        List<UserSymptom> userSymptoms = userSymptomService.getAllUserSymptoms();
        return new ResponseEntity<>(userSymptoms, HttpStatus.OK);
    }

    // GET user symptom by ID - http://localhost:8080/api/user-symptoms/1
    @GetMapping("/{id}")
    public ResponseEntity<UserSymptom> getUserSymptomById(@PathVariable Long id) {
        Optional<UserSymptom> userSymptom = userSymptomService.getUserSymptomById(id);
        if (userSymptom.isPresent()) {
            return new ResponseEntity<>(userSymptom.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POST create new user symptom - http://localhost:8080/api/user-symptoms
    @PostMapping
    public ResponseEntity<UserSymptom> createUserSymptom(@RequestBody UserSymptom userSymptom) {
        UserSymptom savedUserSymptom = userSymptomService.saveUserSymptom(userSymptom);
        return new ResponseEntity<>(savedUserSymptom, HttpStatus.CREATED);
    }

    // PUT update user symptom - http://localhost:8080/api/user-symptoms/1
    @PutMapping("/{id}")
    public ResponseEntity<UserSymptom> updateUserSymptom(@PathVariable Long id, @RequestBody UserSymptom userSymptom) {
        Optional<UserSymptom> existingUserSymptom = userSymptomService.getUserSymptomById(id);
        if (existingUserSymptom.isPresent()) {
            userSymptom.setId(id);
            UserSymptom updatedUserSymptom = userSymptomService.saveUserSymptom(userSymptom);
            return new ResponseEntity<>(updatedUserSymptom, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE user symptom by ID - http://localhost:8080/api/user-symptoms/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSymptom(@PathVariable Long id) {
        Optional<UserSymptom> userSymptom = userSymptomService.getUserSymptomById(id);
        if (userSymptom.isPresent()) {
            userSymptomService.deleteUserSymptom(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET symptoms for specific user - http://localhost:8080/api/user-symptoms/user/1
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSymptom>> getSymptomsByUserId(@PathVariable Long userId) {
        List<UserSymptom> userSymptoms = userSymptomService.getSymptomsByUserId(userId);
        return new ResponseEntity<>(userSymptoms, HttpStatus.OK);
    }

    // GET active symptoms for user - http://localhost:8080/api/user-symptoms/user/1/active
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<UserSymptom>> getActiveSymptomsByUserId(@PathVariable Long userId) {
        List<UserSymptom> activeSymptoms = userSymptomService.getActiveSymptomsByUserId(userId);
        return new ResponseEntity<>(activeSymptoms, HttpStatus.OK);
    }

    // GET users with specific symptom - http://localhost:8080/api/user-symptoms/symptom/1
    @GetMapping("/symptom/{symptomId}")
    public ResponseEntity<List<UserSymptom>> getUsersBySymptomId(@PathVariable Long symptomId) {
        List<UserSymptom> userSymptoms = userSymptomService.getUsersBySymptomId(symptomId);
        return new ResponseEntity<>(userSymptoms, HttpStatus.OK);
    }

    // PUT deactivate user symptom - http://localhost:8080/api/user-symptoms/1/deactivate
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserSymptom> deactivateUserSymptom(@PathVariable Long id) {
        UserSymptom deactivatedSymptom = userSymptomService.deactivateUserSymptom(id);
        if (deactivatedSymptom != null) {
            return new ResponseEntity<>(deactivatedSymptom, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET user symptoms by category - http://localhost:8080/api/user-symptoms/user/1/category/digestive
    @GetMapping("/user/{userId}/category/{category}")
    public ResponseEntity<List<UserSymptom>> getUserSymptomsByCategory(
            @PathVariable Long userId, 
            @PathVariable String category) {
        List<UserSymptom> userSymptoms = userSymptomService.getUserSymptomsByCategory(userId, category);
        return new ResponseEntity<>(userSymptoms, HttpStatus.OK);
    }
}