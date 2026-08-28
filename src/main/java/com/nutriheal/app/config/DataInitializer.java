package com.nutriheal.app.config;

import com.nutriheal.app.model.Food;
import com.nutriheal.app.model.Symptom;
import com.nutriheal.app.repository.FoodRepository;
import com.nutriheal.app.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private SymptomRepository symptomRepository;

    @Override
    public void run(String... args) throws Exception {
        if (foodRepository.count() == 0 && symptomRepository.count() == 0) {
            System.out.println("🌱 Initializing database with food and symptom data...");
            initializeSymptoms();
            initializeFoods();
            createSymptomFoodRelationships();
            System.out.println("✅ Database initialization completed!");
        } else {
            System.out.println("📊 Database already contains data. Skipping initialization.");
        }
    }

    private void initializeSymptoms() {
        List<Symptom> symptoms = Arrays.asList(
            // Digestive Issues
            createSymptom("Bloating & Gas", "Digestive", "Digestive discomfort, abdominal bloating, and gas", 
                "Avoid carbonated drinks, eat slowly, try ginger tea", "moderate"),
            createSymptom("Constipation", "Digestive", "Difficulty with bowel movements and irregular digestion", 
                "Increase fiber intake, drink plenty of water, stay physically active", "moderate"),
            createSymptom("Diarrhea", "Digestive", "Loose, watery bowel movements", 
                "Stay hydrated, eat bland foods, avoid dairy temporarily", "moderate"),
            createSymptom("Acid Reflux", "Digestive", "Burning sensation in chest, heartburn", 
                "Avoid spicy/fatty foods, eat smaller meals, don't lie down after eating", "moderate"),
            createSymptom("Nausea", "Digestive", "Feeling of sickness with urge to vomit", 
                "Eat ginger, try peppermint, small frequent meals", "moderate"),
            createSymptom("Poor Digestion", "Digestive", "General digestive discomfort and irregularity", 
                "Eat probiotic foods, increase fiber, stay hydrated", "low"),
            
            // Energy & Fatigue
            createSymptom("Fatigue & Low Energy", "Energy", "Persistent tiredness and lack of energy throughout the day", 
                "Get adequate sleep, stay hydrated, eat iron-rich foods", "moderate"),
            createSymptom("Weakness", "Energy", "Physical weakness and lack of strength", 
                "Eat protein-rich foods, ensure adequate calories, check iron levels", "moderate"),
            createSymptom("Brain Fog", "Mental Health", "Difficulty concentrating, mental cloudiness", 
                "Stay hydrated, eat omega-3s, get adequate sleep", "low"),
            
            // Cardiovascular
            createSymptom("High Blood Pressure", "Cardiovascular", "Elevated blood pressure readings", 
                "Reduce sodium intake, increase potassium-rich foods, exercise regularly", "high"),
            createSymptom("High Cholesterol", "Cardiovascular", "Elevated cholesterol levels", 
                "Eat more fiber, reduce saturated fats, increase omega-3s", "high"),
            createSymptom("Poor Circulation", "Cardiovascular", "Cold hands/feet, numbness", 
                "Eat foods rich in iron and B vitamins, stay active", "moderate"),
            
            // Mental Health
            createSymptom("Stress & Anxiety", "Mental Health", "Mental stress, anxiety, and nervousness", 
                "Practice mindfulness, eat mood-boosting foods, get adequate omega-3s", "moderate"),
            createSymptom("Low Mood", "Mental Health", "Feelings of sadness or low mood", 
                "Eat foods rich in B vitamins, omega-3s, and tryptophan", "moderate"),
            createSymptom("Depression", "Mental Health", "Persistent feelings of sadness and loss of interest", 
                "Seek professional help, eat mood-supporting foods, regular exercise", "high"),
            createSymptom("Irritability", "Mental Health", "Easy frustration and anger", 
                "Eat blood sugar stabilizing foods, reduce caffeine, get adequate sleep", "low"),
            
            // Sleep Issues
            createSymptom("Sleep Problems", "Sleep", "Difficulty falling asleep or staying asleep", 
                "Avoid caffeine late in day, eat magnesium-rich foods, maintain sleep schedule", "moderate"),
            createSymptom("Insomnia", "Sleep", "Chronic inability to sleep", 
                "Eat tryptophan-rich foods, avoid screens before bed, relaxation techniques", "high"),
            
            // Immune System
            createSymptom("Weak Immunity", "Immune System", "Frequent infections and slow recovery", 
                "Eat vitamin C rich foods, get adequate zinc, maintain balanced diet", "moderate"),
            createSymptom("Frequent Colds", "Immune System", "Getting sick often", 
                "Boost vitamin C, eat probiotic foods, adequate rest", "moderate"),
            createSymptom("Slow Healing", "Immune System", "Wounds take long time to heal", 
                "Increase protein, vitamin C, and zinc intake", "moderate"),
            
            // Musculoskeletal
            createSymptom("Joint Pain", "Musculoskeletal", "Pain and inflammation in joints", 
                "Anti-inflammatory foods, omega-3s, reduce processed foods", "moderate"),
            createSymptom("Muscle Cramps", "Musculoskeletal", "Sudden muscle contractions and pain", 
                "Increase magnesium, potassium, stay hydrated", "low"),
            createSymptom("Back Pain", "Musculoskeletal", "Pain in lower or upper back", 
                "Anti-inflammatory foods, maintain healthy weight, core exercises", "moderate"),
            createSymptom("Osteoporosis Risk", "Musculoskeletal", "Weak bones, fracture risk", 
                "Increase calcium and vitamin D, weight-bearing exercise", "high"),
            
            // Skin Issues
            createSymptom("Acne", "Skin", "Pimples and skin breakouts", 
                "Reduce dairy and sugar, increase zinc, stay hydrated", "low"),
            createSymptom("Dry Skin", "Skin", "Flaky, rough skin", 
                "Increase healthy fats, stay hydrated, eat vitamin E rich foods", "low"),
            createSymptom("Eczema", "Skin", "Itchy, inflamed skin patches", 
                "Anti-inflammatory foods, omega-3s, avoid triggers", "moderate"),
            
            // Metabolic
            createSymptom("Weight Gain", "Metabolic", "Difficulty losing weight or gaining unwanted weight", 
                "Balanced diet, portion control, regular exercise", "moderate"),
            createSymptom("Diabetes Risk", "Metabolic", "High blood sugar, pre-diabetes", 
                "Low glycemic foods, increase fiber, regular exercise", "high"),
            createSymptom("Thyroid Issues", "Metabolic", "Underactive or overactive thyroid", 
                "Eat iodine-rich foods, selenium, avoid goitrogens if hypothyroid", "high"),
            
            // Hormonal
            createSymptom("PMS Symptoms", "Hormonal", "Mood swings, cramps, bloating before period", 
                "Eat magnesium-rich foods, reduce caffeine, increase B vitamins", "moderate"),
            createSymptom("Menopause Symptoms", "Hormonal", "Hot flashes, mood changes", 
                "Eat phytoestrogen foods, stay hydrated, calcium-rich foods", "moderate"),
            
            // Headaches
            createSymptom("Headaches", "Neurological", "Frequent head pain", 
                "Stay hydrated, avoid triggers, eat magnesium-rich foods", "moderate"),
            createSymptom("Migraines", "Neurological", "Severe headaches with nausea", 
                "Identify food triggers, eat regular meals, stay hydrated", "high")
        );

        symptomRepository.saveAll(symptoms);
        System.out.println("✅ Added " + symptoms.size() + " symptoms to database");
    }

    private void initializeFoods() {
        List<Food> foods = new ArrayList<>();

        // === VEGETABLES & LEAFY GREENS ===
        foods.add(createFood("Spinach", "Leafy Green", "Dark leafy green vegetable packed with nutrients",
            23.0, 2.9, 3.6, 0.4, 2.2, 0.0, 0.0, 28.0, 99.0, 2.7, 9377.0, 558.0,
            "High in iron and B-vitamins, boosts energy levels"));
        
        foods.add(createFood("Kale", "Leafy Green", "Nutrient-dense superfood green",
            35.0, 2.9, 4.4, 1.5, 4.1, 0.0, 0.0, 93.0, 254.0, 1.6, 15376.0, 348.0,
            "Antioxidant powerhouse, supports heart and bone health"));
        
        foods.add(createFood("Broccoli", "Vegetable", "Cruciferous vegetable high in vitamins",
            34.0, 2.8, 7.0, 0.4, 2.6, 0.0, 0.0, 89.0, 47.0, 0.7, 623.0, 316.0,
            "Cancer-fighting compounds, high in vitamin C"));
        
        foods.add(createFood("Sweet Potato", "Root Vegetable", "Orange root vegetable rich in beta-carotene",
            86.0, 1.6, 20.0, 0.1, 3.0, 0.0, 0.0, 2.4, 30.0, 0.6, 14187.0, 337.0,
            "Complex carbs, high in fiber and vitamin A"));
        
        foods.add(createFood("Carrots", "Root Vegetable", "Orange vegetable excellent for eyes",
            41.0, 0.9, 10.0, 0.2, 2.8, 0.0, 0.0, 5.9, 33.0, 0.3, 16706.0, 320.0,
            "Beta-carotene for eye health, immune support"));
        
        foods.add(createFood("Beetroot", "Root Vegetable", "Root vegetable high in natural nitrates",
            43.0, 1.6, 9.6, 0.2, 2.8, 0.0, 0.0, 4.9, 16.0, 0.8, 33.0, 325.0,
            "Natural nitrates help lower blood pressure"));
        
        foods.add(createFood("Bell Peppers", "Vegetable", "Colorful peppers high in vitamin C",
            31.0, 1.0, 6.0, 0.3, 2.1, 0.0, 0.0, 127.0, 7.0, 0.4, 3131.0, 211.0,
            "Highest vitamin C content, antioxidant rich"));
        
        foods.add(createFood("Tomatoes", "Vegetable", "Red vegetable high in lycopene",
            18.0, 0.9, 3.9, 0.2, 1.2, 0.0, 0.0, 13.7, 10.0, 0.3, 833.0, 237.0,
            "Lycopene for heart health and cancer prevention"));

        // === FRUITS ===
        foods.add(createFood("Blueberries", "Berries", "Small berries packed with antioxidants",
            57.0, 0.7, 14.0, 0.3, 2.4, 0.0, 0.0, 9.7, 6.0, 0.3, 54.0, 77.0,
            "Antioxidants support brain health and stress response"));
        
        foods.add(createFood("Strawberries", "Berries", "Sweet red berries high in vitamin C",
            32.0, 0.7, 7.7, 0.3, 2.0, 0.0, 0.0, 58.8, 16.0, 0.4, 12.0, 153.0,
            "Vitamin C, heart health, blood sugar control"));
        
        foods.add(createFood("Banana", "Fruit", "Yellow fruit excellent for potassium",
            89.0, 1.1, 23.0, 0.3, 2.6, 0.0, 0.0, 8.7, 5.0, 0.3, 64.0, 358.0,
            "Quick energy, muscle cramp prevention"));
        
        foods.add(createFood("Apples", "Fruit", "Common fruit high in fiber",
            52.0, 0.3, 14.0, 0.2, 2.4, 0.0, 0.0, 4.6, 6.0, 0.1, 54.0, 107.0,
            "Fiber for digestion, heart health"));
        
        foods.add(createFood("Oranges", "Citrus Fruit", "Citrus fruit rich in vitamin C",
            47.0, 0.9, 12.0, 0.1, 2.4, 0.0, 0.0, 53.2, 40.0, 0.1, 225.0, 181.0,
            "Immune boost, collagen production"));
        
        foods.add(createFood("Avocado", "Fruit", "Creamy fruit rich in healthy fats",
            160.0, 2.0, 9.0, 15.0, 7.0, 0.0, 0.0, 10.0, 12.0, 0.6, 146.0, 485.0,
            "Healthy fats for brain and heart health"));
        
        foods.add(createFood("Pomegranate", "Fruit", "Red fruit with powerful antioxidants",
            83.0, 1.7, 19.0, 1.2, 4.0, 0.0, 0.0, 10.2, 10.0, 0.3, 0.0, 236.0,
            "Antioxidants, heart health, anti-inflammatory"));
        
        foods.add(createFood("Papaya", "Tropical Fruit", "Orange tropical fruit with digestive enzymes",
            43.0, 0.5, 11.0, 0.3, 1.7, 0.0, 0.0, 60.9, 20.0, 0.3, 950.0, 182.0,
            "Digestive enzymes, vitamin C, anti-inflammatory"));

        // === PROTEINS (NON-VEG) ===
        foods.add(createFood("Salmon", "Fish", "Fatty fish rich in omega-3 fatty acids",
            208.0, 20.0, 0.0, 13.0, 0.0, 0.0, 3.5, 0.0, 12.0, 0.8, 214.0, 363.0,
            "Omega-3s for heart and brain health"));
        
        foods.add(createFood("Sardines", "Fish", "Small fish packed with calcium",
            208.0, 25.0, 0.0, 11.0, 0.0, 0.0, 2.2, 0.0, 382.0, 2.9, 108.0, 397.0,
            "Omega-3s, calcium for bones, vitamin D"));
        
        foods.add(createFood("Chicken Breast", "Poultry", "Lean protein source",
            165.0, 31.0, 0.0, 3.6, 0.0, 0.0, 0.0, 0.0, 15.0, 1.0, 21.0, 256.0,
            "High protein, low fat, muscle building"));
        
        foods.add(createFood("Eggs", "Protein", "Complete protein with essential nutrients",
            155.0, 13.0, 1.1, 11.0, 0.0, 0.0, 0.1, 0.0, 56.0, 1.8, 540.0, 138.0,
            "Complete protein, brain health, eye health"));
        
        foods.add(createFood("Turkey", "Poultry", "Lean white meat high in tryptophan",
            189.0, 29.0, 0.0, 7.0, 0.0, 0.0, 0.0, 0.0, 21.0, 1.4, 0.0, 249.0,
            "Lean protein, tryptophan for mood and sleep"));

        // === VEGETARIAN PROTEINS ===
        foods.add(createFood("Lentils", "Legumes", "Protein-rich legumes high in fiber",
            116.0, 9.0, 20.0, 0.4, 7.9, 0.0, 0.0, 1.5, 19.0, 3.3, 8.0, 369.0,
            "Plant protein, iron, fiber for digestion"));
        
        foods.add(createFood("Chickpeas", "Legumes", "Versatile legume high in protein",
            164.0, 8.9, 27.0, 2.6, 7.6, 0.0, 0.0, 1.3, 49.0, 2.9, 27.0, 291.0,
            "Plant protein, fiber, blood sugar control"));
        
        foods.add(createFood("Black Beans", "Legumes", "Dark beans rich in antioxidants",
            132.0, 8.9, 24.0, 0.5, 8.7, 0.0, 0.0, 0.0, 27.0, 2.1, 6.0, 355.0,
            "Protein, fiber, heart health"));
        
        foods.add(createFood("Tofu", "Soy Product", "Versatile plant protein from soybeans",
            76.0, 8.0, 1.9, 4.8, 0.3, 0.0, 0.0, 0.1, 350.0, 5.4, 85.0, 121.0,
            "Complete plant protein, calcium, iron"));
        
        foods.add(createFood("Tempeh", "Soy Product", "Fermented soy with probiotics",
            193.0, 19.0, 9.0, 11.0, 0.0, 0.0, 0.0, 0.0, 111.0, 2.7, 0.0, 412.0,
            "High protein, probiotics, fermented benefits"));

        // === WHOLE GRAINS ===
        foods.add(createFood("Quinoa", "Whole Grain", "Complete protein grain with all amino acids",
            368.0, 14.1, 64.0, 6.1, 7.0, 0.0, 0.0, 0.0, 47.0, 4.6, 14.0, 563.0,
            "Complete protein, gluten-free, sustained energy"));
        
        foods.add(createFood("Oats", "Whole Grain", "Whole grain cereal rich in soluble fiber",
            389.0, 16.9, 66.0, 6.9, 10.0, 0.0, 0.0, 0.0, 54.0, 4.7, 0.0, 429.0,
            "Beta-glucan fiber lowers cholesterol"));
        
        foods.add(createFood("Brown Rice", "Whole Grain", "Whole grain rice with bran intact",
            111.0, 2.6, 23.0, 0.9, 1.8, 0.0, 0.0, 0.0, 10.0, 0.4, 0.0, 43.0,
            "Complex carbs, B vitamins, sustained energy"));
        
        foods.add(createFood("Whole Wheat Bread", "Grain Product", "Bread made from whole wheat",
            247.0, 13.0, 41.0, 3.4, 6.0, 0.0, 0.0, 0.0, 107.0, 3.6, 0.0, 248.0,
            "Fiber, B vitamins, complex carbohydrates"));

        // === NUTS & SEEDS ===
        foods.add(createFood("Almonds", "Nuts", "Tree nuts rich in healthy fats",
            579.0, 21.0, 22.0, 50.0, 12.5, 0.0, 0.0, 0.0, 269.0, 3.7, 1.0, 733.0,
            "Healthy fats, vitamin E, heart health"));
        
        foods.add(createFood("Walnuts", "Nuts", "Tree nuts especially high in omega-3s",
            654.0, 15.0, 14.0, 65.0, 6.7, 0.0, 9.1, 1.3, 98.0, 2.9, 20.0, 441.0,
            "Highest plant omega-3s, brain health"));
        
        foods.add(createFood("Chia Seeds", "Seeds", "Tiny seeds packed with omega-3s and fiber",
            486.0, 17.0, 42.0, 31.0, 34.0, 0.0, 4.9, 0.0, 631.0, 7.7, 54.0, 407.0,
            "Omega-3s, fiber, sustained energy"));
        
        foods.add(createFood("Flax Seeds", "Seeds", "Seeds high in omega-3 and lignans",
            534.0, 18.0, 29.0, 42.0, 27.0, 0.0, 22.8, 0.6, 255.0, 5.7, 0.0, 813.0,
            "Omega-3s, fiber, hormonal balance"));
        
        foods.add(createFood("Pumpkin Seeds", "Seeds", "Seeds rich in zinc and magnesium",
            559.0, 30.0, 11.0, 49.0, 6.0, 0.0, 0.0, 1.9, 46.0, 8.8, 16.0, 809.0,
            "Zinc for immunity, magnesium for sleep"));

        // === DAIRY & ALTERNATIVES ===
        foods.add(createFood("Greek Yogurt", "Dairy", "Probiotic-rich fermented dairy",
            59.0, 10.0, 3.6, 0.4, 0.0, 0.0, 0.0, 0.0, 110.0, 0.1, 27.0, 141.0,
            "Probiotics for gut health, high protein"));
        
        foods.add(createFood("Kefir", "Dairy", "Fermented milk drink with probiotics",
            41.0, 3.3, 4.5, 1.0, 0.0, 0.0, 0.0, 0.2, 120.0, 0.1, 68.0, 164.0,
            "Multiple probiotic strains, digestive health"));
        
        foods.add(createFood("Cottage Cheese", "Dairy", "Fresh cheese high in protein",
            98.0, 11.0, 3.4, 4.3, 0.0, 0.0, 0.0, 0.0, 83.0, 0.1, 165.0, 104.0,
            "High protein, low calorie, muscle building"));
        
        foods.add(createFood("Almond Milk", "Plant Milk", "Plant-based milk alternative",
            17.0, 0.6, 1.5, 1.2, 0.3, 0.0, 0.0, 0.0, 184.0, 0.4, 0.0, 67.0,
            "Dairy-free, low calorie, fortified with vitamins"));

        // === HERBS & SPICES ===
        foods.add(createFood("Turmeric", "Spice", "Golden spice with anti-inflammatory properties",
            354.0, 7.8, 65.0, 10.0, 21.0, 0.0, 0.0, 25.9, 183.0, 41.4, 0.0, 2525.0,
            "Curcumin for inflammation, joint health"));
        
        foods.add(createFood("Ginger", "Root Spice", "Spicy root with digestive benefits",
            80.0, 1.8, 18.0, 0.8, 2.0, 0.0, 0.0, 5.0, 16.0, 0.6, 0.0, 415.0,
            "Digestive aid, anti-nausea, anti-inflammatory"));
        
        foods.add(createFood("Garlic", "Vegetable/Spice", "Pungent bulb with cardiovascular benefits",
            149.0, 6.4, 33.0, 0.5, 2.1, 0.0, 0.0, 31.0, 181.0, 1.7, 9.0, 401.0,
            "Heart health, immune support, antimicrobial"));
        
        foods.add(createFood("Cinnamon", "Spice", "Sweet spice for blood sugar control",
            247.0, 4.0, 81.0, 1.2, 53.0, 0.0, 0.0, 3.8, 1002.0, 8.3, 295.0, 431.0,
            "Blood sugar control, antioxidants"));

        // === BEVERAGES & TEAS ===
        foods.add(createFood("Green Tea", "Beverage", "Tea rich in antioxidants",
            1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 8.0,
            "Antioxidants, metabolism boost, brain health"));
        
        foods.add(createFood("Chamomile Tea", "Beverage", "Herbal tea with calming properties",
            1.0, 0.0, 0.2, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 9.0,
            "Relaxation, sleep aid, digestive comfort"));
        
        foods.add(createFood("Peppermint Tea", "Beverage", "Herbal tea for digestion",
            2.0, 0.1, 0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            "Digestive relief, reduces bloating"));
        
        foods.add(createFood("Ginger Tea", "Beverage", "Warming tea for digestion",
            2.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 0.5, 2.0, 0.0, 0.0, 10.0,
            "Anti-nausea, digestive aid, warming"));

        // === HEALTHY FATS ===
        foods.add(createFood("Olive Oil", "Healthy Fat", "Monounsaturated fat source",
            884.0, 0.0, 0.0, 100.0, 0.0, 0.0, 0.8, 0.0, 1.0, 0.6, 0.0, 1.0,
            "Heart health, anti-inflammatory"));
        
        foods.add(createFood("Coconut Oil", "Healthy Fat", "Tropical oil with MCTs",
            862.0, 0.0, 0.0, 100.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            "Quick energy, metabolism boost"));

        // === FERMENTED FOODS ===
        foods.add(createFood("Kimchi", "Fermented Vegetable", "Spicy fermented cabbage",
            15.0, 1.1, 2.4, 0.5, 1.6, 0.0, 0.0, 18.0, 33.0, 2.5, 18.0, 151.0,
            "Probiotics, gut health, immune support"));
        
        foods.add(createFood("Sauerkraut", "Fermented Vegetable", "Fermented cabbage",
            19.0, 0.9, 4.3, 0.1, 2.9, 0.0, 0.0, 15.0, 30.0, 1.5, 15.0, 170.0,
            "Probiotics, vitamin C, digestive health"));

        // === SUPERFOODS ===
        foods.add(createFood("Spirulina", "Algae", "Blue-green algae superfood",
            290.0, 57.0, 24.0, 8.0, 3.6, 0.0, 0.0, 10.0, 120.0, 28.5, 570.0, 1363.0,
            "Complete protein, detoxification, energy"));
        
        foods.add(createFood("Acai Berries", "Berries", "Antioxidant-rich berries",
            70.0, 2.0, 4.0, 5.0, 2.0, 0.0, 0.0, 0.0, 35.0, 0.8, 1000.0, 124.0,
            "Antioxidants, energy, anti-aging"));
        
        foods.add(createFood("Dark Chocolate", "Confection", "High cocoa chocolate with flavonoids",
            546.0, 4.9, 61.0, 31.0, 7.0, 0.0, 0.0, 0.0, 73.0, 11.9, 50.0, 559.0,
            "Antioxidants, heart health, mood boost"));

        foodRepository.saveAll(foods);
        System.out.println("✅ Added " + foods.size() + " foods to database");
    }

    private void createSymptomFoodRelationships() {
        List<Symptom> symptoms = symptomRepository.findAll();
        List<Food> foods = foodRepository.findAll();

        for (Symptom symptom : symptoms) {
            List<Food> recommendedFoods = new ArrayList<>();

            switch (symptom.getName()) {
                case "Bloating & Gas":
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Peppermint Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Fennel Seeds"));
                    recommendedFoods.add(findFoodByName(foods, "Papaya"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger Tea"));
                    break;

                case "Constipation":
                    recommendedFoods.add(findFoodByName(foods, "Prunes"));
                    recommendedFoods.add(findFoodByName(foods, "Oats"));
                    recommendedFoods.add(findFoodByName(foods, "Flax Seeds"));
                    recommendedFoods.add(findFoodByName(foods, "Chia Seeds"));
                    recommendedFoods.add(findFoodByName(foods, "Papaya"));
                    recommendedFoods.add(findFoodByName(foods, "Spinach"));
                    break;

                case "Diarrhea":
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    recommendedFoods.add(findFoodByName(foods, "Brown Rice"));
                    recommendedFoods.add(findFoodByName(foods, "Oats"));
                    recommendedFoods.add(findFoodByName(foods, "Chamomile Tea"));
                    break;

                case "Acid Reflux":
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Oats"));
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    recommendedFoods.add(findFoodByName(foods, "Chamomile Tea"));
                    break;

                case "Nausea":
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Peppermint Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    break;

                case "Poor Digestion":
                    recommendedFoods.add(findFoodByName(foods, "Greek Yogurt"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Papaya"));
                    recommendedFoods.add(findFoodByName(foods, "Kimchi"));
                    recommendedFoods.add(findFoodByName(foods, "Kefir"));
                    break;

                case "Fatigue & Low Energy":
                    recommendedFoods.add(findFoodByName(foods, "Spinach"));
                    recommendedFoods.add(findFoodByName(foods, "Quinoa"));
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Eggs"));
                    recommendedFoods.add(findFoodByName(foods, "Sweet Potato"));
                    recommendedFoods.add(findFoodByName(foods, "Spirulina"));
                    break;

                case "Weakness":
                    recommendedFoods.add(findFoodByName(foods, "Chicken Breast"));
                    recommendedFoods.add(findFoodByName(foods, "Eggs"));
                    recommendedFoods.add(findFoodByName(foods, "Lentils"));
                    recommendedFoods.add(findFoodByName(foods, "Quinoa"));
                    recommendedFoods.add(findFoodByName(foods, "Spirulina"));
                    break;

                case "Brain Fog":
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Walnuts"));
                    recommendedFoods.add(findFoodByName(foods, "Blueberries"));
                    recommendedFoods.add(findFoodByName(foods, "Avocado"));
                    recommendedFoods.add(findFoodByName(foods, "Green Tea"));
                    break;

                case "High Blood Pressure":
                    recommendedFoods.add(findFoodByName(foods, "Beetroot"));
                    recommendedFoods.add(findFoodByName(foods, "Garlic"));
                    recommendedFoods.add(findFoodByName(foods, "Dark Chocolate"));
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    recommendedFoods.add(findFoodByName(foods, "Spinach"));
                    recommendedFoods.add(findFoodByName(foods, "Pomegranate"));
                    break;

                case "High Cholesterol":
                    recommendedFoods.add(findFoodByName(foods, "Oats"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Olive Oil"));
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Avocado"));
                    break;

                case "Poor Circulation":
                    recommendedFoods.add(findFoodByName(foods, "Beetroot"));
                    recommendedFoods.add(findFoodByName(foods, "Garlic"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Dark Chocolate"));
                    break;

                case "Stress & Anxiety":
                    recommendedFoods.add(findFoodByName(foods, "Blueberries"));
                    recommendedFoods.add(findFoodByName(foods, "Chamomile Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Avocado"));
                    recommendedFoods.add(findFoodByName(foods, "Dark Chocolate"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Green Tea"));
                    break;

                case "Low Mood":
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Dark Chocolate"));
                    recommendedFoods.add(findFoodByName(foods, "Avocado"));
                    recommendedFoods.add(findFoodByName(foods, "Blueberries"));
                    recommendedFoods.add(findFoodByName(foods, "Walnuts"));
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    break;

                case "Depression":
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Walnuts"));
                    recommendedFoods.add(findFoodByName(foods, "Turmeric"));
                    recommendedFoods.add(findFoodByName(foods, "Dark Leafy Greens"));
                    recommendedFoods.add(findFoodByName(foods, "Turkey"));
                    break;

                case "Irritability":
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    recommendedFoods.add(findFoodByName(foods, "Oats"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Chamomile Tea"));
                    break;

                case "Sleep Problems":
                    recommendedFoods.add(findFoodByName(foods, "Chamomile Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Kale"));
                    recommendedFoods.add(findFoodByName(foods, "Turkey"));
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    break;

                case "Insomnia":
                    recommendedFoods.add(findFoodByName(foods, "Chamomile Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Turkey"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Kiwi"));
                    recommendedFoods.add(findFoodByName(foods, "Pumpkin Seeds"));
                    break;

                case "Weak Immunity":
                    recommendedFoods.add(findFoodByName(foods, "Oranges"));
                    recommendedFoods.add(findFoodByName(foods, "Greek Yogurt"));
                    recommendedFoods.add(findFoodByName(foods, "Turmeric"));
                    recommendedFoods.add(findFoodByName(foods, "Green Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Garlic"));
                    recommendedFoods.add(findFoodByName(foods, "Spinach"));
                    break;

                case "Frequent Colds":
                    recommendedFoods.add(findFoodByName(foods, "Oranges"));
                    recommendedFoods.add(findFoodByName(foods, "Bell Peppers"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Garlic"));
                    recommendedFoods.add(findFoodByName(foods, "Kimchi"));
                    break;

                case "Slow Healing":
                    recommendedFoods.add(findFoodByName(foods, "Eggs"));
                    recommendedFoods.add(findFoodByName(foods, "Chicken Breast"));
                    recommendedFoods.add(findFoodByName(foods, "Oranges"));
                    recommendedFoods.add(findFoodByName(foods, "Pumpkin Seeds"));
                    break;

                case "Joint Pain":
                    recommendedFoods.add(findFoodByName(foods, "Turmeric"));
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Walnuts"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Blueberries"));
                    break;

                case "Muscle Cramps":
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    recommendedFoods.add(findFoodByName(foods, "Spinach"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Sweet Potato"));
                    break;

                case "Back Pain":
                    recommendedFoods.add(findFoodByName(foods, "Turmeric"));
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Cherries"));
                    break;

                case "Osteoporosis Risk":
                    recommendedFoods.add(findFoodByName(foods, "Sardines"));
                    recommendedFoods.add(findFoodByName(foods, "Kale"));
                    recommendedFoods.add(findFoodByName(foods, "Greek Yogurt"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Broccoli"));
                    break;

                case "Acne":
                    recommendedFoods.add(findFoodByName(foods, "Pumpkin Seeds"));
                    recommendedFoods.add(findFoodByName(foods, "Green Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Turmeric"));
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    break;

                case "Dry Skin":
                    recommendedFoods.add(findFoodByName(foods, "Avocado"));
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Walnuts"));
                    recommendedFoods.add(findFoodByName(foods, "Sweet Potato"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    break;

                case "Eczema":
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Flax Seeds"));
                    recommendedFoods.add(findFoodByName(foods, "Turmeric"));
                    recommendedFoods.add(findFoodByName(foods, "Probiotics"));
                    break;

                case "Weight Gain":
                    recommendedFoods.add(findFoodByName(foods, "Quinoa"));
                    recommendedFoods.add(findFoodByName(foods, "Chicken Breast"));
                    recommendedFoods.add(findFoodByName(foods, "Broccoli"));
                    recommendedFoods.add(findFoodByName(foods, "Green Tea"));
                    recommendedFoods.add(findFoodByName(foods, "Greek Yogurt"));
                    break;

                case "Diabetes Risk":
                    recommendedFoods.add(findFoodByName(foods, "Cinnamon"));
                    recommendedFoods.add(findFoodByName(foods, "Oats"));
                    recommendedFoods.add(findFoodByName(foods, "Lentils"));
                    recommendedFoods.add(findFoodByName(foods, "Broccoli"));
                    recommendedFoods.add(findFoodByName(foods, "Chia Seeds"));
                    break;

                case "Thyroid Issues":
                    recommendedFoods.add(findFoodByName(foods, "Sardines"));
                    recommendedFoods.add(findFoodByName(foods, "Eggs"));
                    recommendedFoods.add(findFoodByName(foods, "Pumpkin Seeds"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    break;

                case "PMS Symptoms":
                    recommendedFoods.add(findFoodByName(foods, "Dark Chocolate"));
                    recommendedFoods.add(findFoodByName(foods, "Spinach"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Banana"));
                    recommendedFoods.add(findFoodByName(foods, "Chamomile Tea"));
                    break;

                case "Menopause Symptoms":
                    recommendedFoods.add(findFoodByName(foods, "Flax Seeds"));
                    recommendedFoods.add(findFoodByName(foods, "Tofu"));
                    recommendedFoods.add(findFoodByName(foods, "Greek Yogurt"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    break;

                case "Headaches":
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Spinach"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Peppermint Tea"));
                    break;

                case "Migraines":
                    recommendedFoods.add(findFoodByName(foods, "Salmon"));
                    recommendedFoods.add(findFoodByName(foods, "Ginger"));
                    recommendedFoods.add(findFoodByName(foods, "Spinach"));
                    recommendedFoods.add(findFoodByName(foods, "Almonds"));
                    recommendedFoods.add(findFoodByName(foods, "Pumpkin Seeds"));
                    break;
            }

            // Filter out null values
            recommendedFoods.removeIf(food -> food == null);
            symptom.setRecommendedFoods(recommendedFoods);
            symptomRepository.save(symptom);
        }

        System.out.println("✅ Created symptom-food relationships");
    }

    private Symptom createSymptom(String name, String category, String description, 
                                   String nutritionalGuidance, String severity) {
        Symptom symptom = new Symptom();
        symptom.setName(name);
        symptom.setCategory(category);
        symptom.setDescription(description);
        symptom.setNutritionalGuidance(nutritionalGuidance);
        symptom.setSeverity(severity);
        return symptom;
    }

    private Food createFood(String name, String category, String description,
                           Double calories, Double protein, Double carbohydrates, Double fat,
                           Double fiber, Double sugar, Double omega3, Double vitaminC,
                           Double calcium, Double iron, Double vitaminA, Double potassium) {
        Food food = new Food();
        food.setName(name);
        food.setCategory(category);
        food.setDescription(description);
        food.setCalories(calories);
        food.setProtein(protein);
        food.setCarbohydrates(carbohydrates);
        food.setFat(fat);
        food.setFiber(fiber);
        food.setSugar(sugar);
        food.setOmega3(omega3);
        food.setVitaminC(vitaminC);
        food.setCalcium(calcium);
        food.setIron(iron);
        food.setVitaminA(vitaminA);
        food.setPotassium(potassium);
        return food;
    }

    private Food createFood(String name, String category, String description,
                           Double calories, Double protein, Double carbohydrates, Double fat,
                           Double fiber, Double sugar, Double omega3, Double vitaminC,
                           Double calcium, Double iron, Double vitaminA, Double potassium,
                           String healthBenefits) {
        Food food = createFood(name, category, description, calories, protein, carbohydrates,
                              fat, fiber, sugar, omega3, vitaminC, calcium, iron, vitaminA, potassium);
        food.setHealthBenefits(healthBenefits);
        return food;
    }

    private Food findFoodByName(List<Food> foods, String name) {
        return foods.stream()
                .filter(f -> f.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}