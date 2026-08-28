package com.nutriheal.app.controller;

import com.nutriheal.app.model.Symptom;
import com.nutriheal.app.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/symptoms")
@CrossOrigin(origins = "*")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    // GET all symptoms - http://localhost:8080/api/symptoms
    @GetMapping
    public ResponseEntity<List<Symptom>> getAllSymptoms() {
        List<Symptom> symptoms = symptomService.getAllSymptoms();
        return new ResponseEntity<>(symptoms, HttpStatus.OK);
    }

    // GET symptom by ID - http://localhost:8080/api/symptoms/1
    @GetMapping("/{id}")
    public ResponseEntity<Symptom> getSymptomById(@PathVariable Long id) {
        Optional<Symptom> symptom = symptomService.getSymptomById(id);
        if (symptom.isPresent()) {
            return new ResponseEntity<>(symptom.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POST create new symptom - http://localhost:8080/api/symptoms
    @PostMapping
    public ResponseEntity<Symptom> createSymptom(@RequestBody Symptom symptom) {
        if (symptomService.existsByName(symptom.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Symptom already exists
        }
        Symptom savedSymptom = symptomService.saveSymptom(symptom);
        return new ResponseEntity<>(savedSymptom, HttpStatus.CREATED);
    }

    // PUT update symptom - http://localhost:8080/api/symptoms/1
    @PutMapping("/{id}")
    public ResponseEntity<Symptom> updateSymptom(@PathVariable Long id, @RequestBody Symptom symptom) {
        Optional<Symptom> existingSymptom = symptomService.getSymptomById(id);
        if (existingSymptom.isPresent()) {
            symptom.setId(id);
            Symptom updatedSymptom = symptomService.saveSymptom(symptom);
            return new ResponseEntity<>(updatedSymptom, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE symptom by ID - http://localhost:8080/api/symptoms/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSymptom(@PathVariable Long id) {
        Optional<Symptom> symptom = symptomService.getSymptomById(id);
        if (symptom.isPresent()) {
            symptomService.deleteSymptom(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET symptoms by category - http://localhost:8080/api/symptoms/category/digestive
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Symptom>> getSymptomsByCategory(@PathVariable String category) {
        List<Symptom> symptoms = symptomService.getSymptomsByCategory(category);
        return new ResponseEntity<>(symptoms, HttpStatus.OK);
    }

    // GET search symptoms by name - http://localhost:8080/api/symptoms/search?name=headache
    @GetMapping("/search")
    public ResponseEntity<List<Symptom>> searchSymptoms(@RequestParam String name) {
        List<Symptom> symptoms = symptomService.searchSymptomsByName(name);
        return new ResponseEntity<>(symptoms, HttpStatus.OK);
    }

    // GET symptom by exact name - http://localhost:8080/api/symptoms/name/headache
    @GetMapping("/name/{name}")
    public ResponseEntity<Symptom> getSymptomByName(@PathVariable String name) {
        Symptom symptom = symptomService.getSymptomByName(name);
        if (symptom != null) {
            return new ResponseEntity<>(symptom, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // GET symptoms by severity - http://localhost:8080/api/symptoms/severity/moderate
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<Symptom>> getSymptomsBySeverity(@PathVariable String severity) {
        List<Symptom> symptoms = symptomService.getSymptomsBySeverity(severity);
        return new ResponseEntity<>(symptoms, HttpStatus.OK);
    }
}