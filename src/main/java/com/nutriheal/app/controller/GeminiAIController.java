package com.nutriheal.app.controller;

import com.nutriheal.app.model.Food;
import com.nutriheal.app.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class GeminiAIController {

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    @Autowired
    private FoodService foodService;

    // Use Flash instead of Pro - Flash is faster and doesn't waste tokens on thinking
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent";
    
    @PostMapping("/recommendations")
    public ResponseEntity<Map<String, Object>> getAIRecommendations(@RequestBody Map<String, Object> userContext) {
        
        if (geminiApiKey == null || geminiApiKey.isEmpty()) {
            System.out.println("⚠️ Gemini API key not configured. Using database recommendations.");
            return ResponseEntity.ok(getFallbackRecommendations(userContext));
        }

        try {
            System.out.println("🤖 Calling Gemini API...");
            
            String prompt = buildNutritionPrompt(userContext);
            
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            
            List<Map<String, Object>> contents = new ArrayList<>();
            Map<String, Object> content = new HashMap<>();
            
            List<Map<String, String>> parts = new ArrayList<>();
            parts.add(Map.of("text", prompt));
            
            content.put("parts", parts);
            contents.add(content);
            
            requestBody.put("contents", contents);
            
            Map<String, Object> generationConfig = new HashMap<>();
            generationConfig.put("temperature", 0.7);
            generationConfig.put("topK", 40);
            generationConfig.put("topP", 0.95);
            generationConfig.put("maxOutputTokens", 2048); // Increased for better responses
            requestBody.put("generationConfig", generationConfig);

            String urlWithKey = GEMINI_API_URL + "?key=" + geminiApiKey;
            
            System.out.println("📡 Request URL: " + GEMINI_API_URL);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(urlWithKey, request, Map.class);

            System.out.println("✅ Gemini API call successful!");
            System.out.println("📦 Response: " + response); // Debug log
            
            return ResponseEntity.ok(parseGeminiResponse(response, userContext));
            
        } catch (Exception e) {
            System.err.println("❌ Gemini API Error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("🔄 Falling back to database recommendations...");
            return ResponseEntity.ok(getFallbackRecommendations(userContext));
        }
    }

    private String buildNutritionPrompt(Map<String, Object> context) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a professional nutritionist AI assistant. ");
        prompt.append("Provide personalized food recommendations in a structured format.\n\n");
        
        prompt.append("User Profile:\n");
        prompt.append("- Age: ").append(context.getOrDefault("age", "N/A")).append("\n");
        prompt.append("- Gender: ").append(context.getOrDefault("gender", "N/A")).append("\n");
        prompt.append("- Activity Level: ").append(context.getOrDefault("activityLevel", "moderate")).append("\n");
        prompt.append("- Dietary Preference: ").append(context.getOrDefault("dietaryPreference", "balanced")).append("\n");
        prompt.append("- Weight: ").append(context.getOrDefault("weight", "N/A")).append(" kg\n");
        prompt.append("- Height: ").append(context.getOrDefault("height", "N/A")).append(" cm\n");
        prompt.append("- Health Goals: ").append(context.getOrDefault("healthGoals", "general health")).append("\n");
        prompt.append("- Allergies: ").append(context.getOrDefault("allergies", "none")).append("\n");
        prompt.append("- Medical Conditions: ").append(context.getOrDefault("medicalConditions", "none")).append("\n");
        prompt.append("- Current Symptoms: ").append(context.getOrDefault("symptoms", "none")).append("\n\n");
        
        prompt.append("Please provide 10 t0 12 specific food recommendations. For EACH food, use this EXACT format:\n\n");
        prompt.append("FOOD: [Food Name]\n");
        prompt.append("REASON: [Why this food is recommended for this person]\n");
        prompt.append("BENEFITS: [Specific health benefits]\n");
        prompt.append("NUTRIENTS: [Key nutrients like protein, fiber, vitamins]\n\n");
        
        prompt.append("After all food recommendations, provide:\n");
        prompt.append("INSIGHTS: [General nutritional advice and tips for this person]\n\n");
        
        prompt.append("Remember to consider their dietary preference and avoid any allergens mentioned.");
        
        return prompt.toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseGeminiResponse(Map<String, Object> response, Map<String, Object> userContext) {
        try {
            System.out.println("🔍 Parsing Gemini response...");
            
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            
            if (candidates == null || candidates.isEmpty()) {
                System.err.println("❌ No candidates in response");
                return getFallbackRecommendations(userContext);
            }
            
            Map<String, Object> candidate = candidates.get(0);
            Map<String, Object> content = (Map<String, Object>) candidate.get("content");
            
            if (content == null) {
                System.err.println("❌ No content in candidate");
                return getFallbackRecommendations(userContext);
            }
            
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            
            if (parts == null || parts.isEmpty()) {
                System.err.println("❌ No parts in content");
                return getFallbackRecommendations(userContext);
            }
            
            String aiText = (String) parts.get(0).get("text");
            
            if (aiText == null || aiText.trim().isEmpty()) {
                System.err.println("❌ Empty AI text");
                return getFallbackRecommendations(userContext);
            }
            
            System.out.println("📝 AI Response Text:\n" + aiText);
            
            // Extract recommendations using improved parsing
            List<Map<String, Object>> recommendations = extractRecommendationsFromText(aiText);
            
            // Extract insights
            String insights = extractInsights(aiText);
            
            if (recommendations.isEmpty()) {
                System.err.println("⚠️ No recommendations extracted, using fallback");
                return getFallbackRecommendations(userContext);
            }
            
            System.out.println("✅ Successfully parsed " + recommendations.size() + " recommendations");
            
            Map<String, Object> result = new HashMap<>();
            result.put("recommendations", recommendations);
            result.put("aiInsights", insights);
            result.put("status", "success");
            result.put("source", "Google Gemini 2.5 Pro AI");
            
            return result;
            
        } catch (Exception e) {
            System.err.println("❌ Error parsing Gemini response: " + e.getMessage());
            e.printStackTrace();
            return getFallbackRecommendations(userContext);
        }
    }

    private List<Map<String, Object>> extractRecommendationsFromText(String aiText) {
        List<Map<String, Object>> recommendations = new ArrayList<>();
        
        try {
            // Split by FOOD: marker
            String[] foodBlocks = aiText.split("FOOD:");
            
            for (int i = 1; i < foodBlocks.length && recommendations.size() < 8; i++) {
                String block = foodBlocks[i].trim();
                
                Map<String, Object> rec = new HashMap<>();
                
                // Extract food name (first line)
                String[] lines = block.split("\n");
                if (lines.length > 0) {
                    String foodName = lines[0].trim().replaceAll("\\*+", "");
                    rec.put("name", foodName);
                    
                    // Extract REASON
                    String reason = extractSection(block, "REASON:");
                    if (!reason.isEmpty()) {
                        rec.put("description", reason);
                    }
                    
                    // Extract BENEFITS
                    String benefits = extractSection(block, "BENEFITS:");
                    if (!benefits.isEmpty()) {
                        rec.put("benefits", benefits);
                    }
                    
                    // Extract NUTRIENTS
                    String nutrients = extractSection(block, "NUTRIENTS:");
                    if (!nutrients.isEmpty()) {
                        rec.put("nutrients", nutrients);
                    }
                    
                    rec.put("source", "Gemini AI");
                    recommendations.add(rec);
                }
            }
            
            // Fallback: Try numbered list format
            if (recommendations.isEmpty()) {
                String[] lines = aiText.split("\n");
                for (String line : lines) {
                    line = line.trim();
                    if (line.matches("^[0-9]+[.)].*") && recommendations.size() < 8) {
                        Map<String, Object> rec = new HashMap<>();
                        
                        String foodName = line.replaceAll("^[0-9]+[.)]\\s*\\*{0,2}", "")
                                             .split(":")[0]
                                             .split("-")[0]
                                             .replaceAll("\\*", "")
                                             .trim();
                        
                        if (!foodName.isEmpty() && foodName.length() < 50) {
                            rec.put("name", foodName);
                            rec.put("description", line);
                            rec.put("benefits", "Recommended by AI based on your profile");
                            rec.put("source", "Gemini AI");
                            recommendations.add(rec);
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error extracting recommendations: " + e.getMessage());
        }
        
        return recommendations;
    }
    
    private String extractSection(String text, String marker) {
        try {
            int start = text.indexOf(marker);
            if (start == -1) return "";
            
            start += marker.length();
            
            // Find next section marker or end
            int end = text.length();
            String[] markers = {"REASON:", "BENEFITS:", "NUTRIENTS:", "FOOD:", "INSIGHTS:"};
            for (String m : markers) {
                if (m.equals(marker)) continue;
                int pos = text.indexOf(m, start);
                if (pos != -1 && pos < end) {
                    end = pos;
                }
            }
            
            String content = text.substring(start, end).trim();
            return content.replaceAll("\\*+", "").trim();
            
        } catch (Exception e) {
            return "";
        }
    }
    
    private String extractInsights(String aiText) {
        int insightsStart = aiText.indexOf("INSIGHTS:");
        if (insightsStart != -1) {
            return aiText.substring(insightsStart + 9).trim().replaceAll("\\*+", "");
        }
        
        // Fallback: Return last paragraph
        String[] paragraphs = aiText.split("\n\n");
        if (paragraphs.length > 0) {
            String lastPara = paragraphs[paragraphs.length - 1].trim();
            if (!lastPara.startsWith("FOOD:") && lastPara.length() > 50) {
                return lastPara;
            }
        }
        
        return "Based on your profile, these foods will help support your health goals and manage your symptoms effectively.";
    }

    private Map<String, Object> getFallbackRecommendations(Map<String, Object> userContext) {
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> symptoms = (List<Map<String, Object>>) userContext.get("symptoms");
            
            Set<Food> allFoods = new HashSet<>();
            
            if (symptoms != null && !symptoms.isEmpty()) {
                for (Map<String, Object> symptom : symptoms) {
                    Object symptomId = symptom.get("id");
                    if (symptomId != null) {
                        try {
                            Long id = Long.parseLong(symptomId.toString());
                            List<Food> foods = foodService.getFoodsForSymptom(id);
                            allFoods.addAll(foods);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid symptom ID: " + symptomId);
                        }
                    }
                }
            }
            
            if (allFoods.isEmpty()) {
                allFoods.addAll(foodService.getAllFoods().stream().limit(10).collect(Collectors.toList()));
            }
            
            List<Map<String, Object>> recommendations = allFoods.stream()
                .limit(10)
                .map(food -> {
                    Map<String, Object> rec = new HashMap<>();
                    rec.put("name", food.getName());
                    rec.put("description", food.getDescription());
                    rec.put("benefits", food.getHealthBenefits());
                    rec.put("category", food.getCategory());
                    
                    List<String> nutrients = new ArrayList<>();
                    if (food.getProtein() != null && food.getProtein() > 0) 
                        nutrients.add("Protein: " + food.getProtein() + "g");
                    if (food.getFiber() != null && food.getFiber() > 0) 
                        nutrients.add("Fiber: " + food.getFiber() + "g");
                    if (food.getVitaminC() != null && food.getVitaminC() > 0) 
                        nutrients.add("Vitamin C");
                    if (food.getIron() != null && food.getIron() > 0) 
                        nutrients.add("Iron");
                    if (food.getCalcium() != null && food.getCalcium() > 0) 
                        nutrients.add("Calcium");
                    
                    rec.put("nutrients", nutrients);
                    rec.put("calories", food.getCalories());
                    rec.put("protein", food.getProtein());
                    rec.put("fiber", food.getFiber());
                    rec.put("vitaminC", food.getVitaminC());
                    rec.put("iron", food.getIron());
                    rec.put("calcium", food.getCalcium());
                    
                    return rec;
                })
                .collect(Collectors.toList());
            
            String insights = buildFallbackInsights(userContext, allFoods.size());
            
            Map<String, Object> result = new HashMap<>();
            result.put("recommendations", recommendations);
            result.put("aiInsights", insights);
            result.put("status", "fallback");
            result.put("source", "Database");
            
            return result;
            
        } catch (Exception e) {
            System.err.println("Error generating fallback recommendations: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> emptyResult = new HashMap<>();
            emptyResult.put("recommendations", new ArrayList<>());
            emptyResult.put("aiInsights", "Unable to generate recommendations at this time. Please try again later.");
            emptyResult.put("status", "error");
            return emptyResult;
        }
    }
    
    private String buildFallbackInsights(Map<String, Object> context, int foodCount) {
        StringBuilder insights = new StringBuilder();
        insights.append("Based on your profile and symptoms, we've recommended ");
        insights.append(foodCount).append(" foods from our nutrition database. ");
        
        String dietPref = (String) context.get("dietaryPreference");
        if (dietPref != null && !dietPref.isEmpty()) {
            insights.append("All recommendations are suitable for a ").append(dietPref).append(" diet. ");
        }
        
        String allergies = (String) context.get("allergies");
        if (allergies != null && !allergies.isEmpty()) {
            insights.append("Please verify these foods don't conflict with your allergies (").append(allergies).append("). ");
        }
        
        insights.append("These foods are selected based on their nutritional profiles and health benefits for your specific symptoms.");
        
        return insights.toString();
    }
}