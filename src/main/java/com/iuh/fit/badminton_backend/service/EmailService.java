package com.iuh.fit.badminton_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(String to, String subject, String text);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(String to, String subject, String text, String pathToAttachment);
}
