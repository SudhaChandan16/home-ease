package com.broomies.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendHtmlEmail(@NonNull String to, @NonNull String subject, @NonNull String templateName, Map<String, Object> variables) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String htmlContent = generateHtmlContent(templateName, variables);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(Objects.requireNonNull(htmlContent), true); // true = HTML
            helper.setFrom("no-reply@homeease.com");

            mailSender.send(mimeMessage);
            System.out.println("Email sent successfully to: " + to);

        } catch (Exception e) {
            // Log the error but do not throw it to prevent breaking the flow
            System.err.println("Failed to send email to: " + to + ". Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String generateHtmlContent(String templateName, Map<String, Object> variables) {
        if ("email/booking-request".equals(templateName)) {
            return "<h3>New Booking Request</h3>" +
                   "<p>Hello " + variables.get("providerName") + ",</p>" +
                   "<p>You have a new booking request from " + variables.get("customerName") + ".</p>" +
                   "<p><strong>Date:</strong> " + variables.get("serviceDate") + "</p>" +
                   "<p><strong>Address:</strong> " + variables.get("address") + "</p>" +
                   "<p><a href='" + variables.get("acceptUrl") + "'>Accept</a> | " +
                   "<a href='" + variables.get("rejectUrl") + "'>Reject</a></p>";
        } else if ("email/booking-status".equals(templateName)) {
            return "<h3>Booking Status Update</h3>" +
                   "<p>Hello " + variables.get("userName") + ",</p>" +
                   "<p>Your booking with " + variables.get("providerName") + " on " + variables.get("serviceDate") + 
                   " has been <strong>" + variables.get("status") + "</strong>.</p>";
        }
        return "<p>You have a new notification.</p>";
    }
}
