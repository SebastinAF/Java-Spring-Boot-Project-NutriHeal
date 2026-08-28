package com.nutriheal.app.controller;

import com.nutriheal.app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private EmailService emailService;

    // Temporary in-memory storage (email → otp)
    private final Map<String, String> otpStorage = new HashMap<>();

    // Step 1: Send OTP
    
    @PostMapping("/send-otp")
    public Map<String, Object> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Map<String, Object> response = new HashMap<>();

        try {
            // Generate random 6-digit OTP
            String otp = String.format("%06d", new Random().nextInt(999999));

            // Store OTP temporarily
            otpStorage.put(email, otp);

            // Send OTP email
            emailService.sendOtpEmail(email, otp);

            response.put("success", true);
            response.put("message", "OTP sent to your email!");
        } catch (Exception e) {
            // If email sending fails, remove from storage
            otpStorage.remove(email);
            
            response.put("success", false);
            response.put("message", "Failed to send OTP. Please try again.");
            
            // Log the error for debugging
            System.err.println("Error sending OTP to " + email + ": " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    // Step 2: Verify OTP
    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String enteredOtp = request.get("otp");

        String storedOtp = otpStorage.get(email);

        Map<String, Object> response = new HashMap<>();

        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
            // OTP matched — clear it
            otpStorage.remove(email);
            response.put("success", true);
            response.put("message", "OTP verified successfully!");
        } else {
            response.put("success", false);
            response.put("message", "Invalid or expired OTP!");
        }

        return response;
    }
}