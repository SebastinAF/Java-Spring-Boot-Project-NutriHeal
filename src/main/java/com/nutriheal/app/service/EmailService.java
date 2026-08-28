package com.nutriheal.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class EmailService {
	
	@PostConstruct
	public void init() {
	    System.out.println("EmailService initialized successfully!");
	}
	
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("asntheprince098@gmail.com"); 
        message.setTo(toEmail);
        message.setSubject("Your NutriHeal OTP Code");
        message.setText("Your OTP code is: " + otp + "\nThis code expires in 5 minutes.");
        mailSender.send(message);
    }
}
