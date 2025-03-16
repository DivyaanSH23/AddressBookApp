package com.bridgeLabz.AddressBookApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // Registration Email
    public void sendRegistrationEmail(String toEmail, String firstName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("divyanshshuklaofficial23@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Welcome to Address Book App! 🚀");
        message.setText("Dear " + firstName + ",\n\nYour registration was successful. Enjoy using our platform!\n\nBest Regards,\nAddressBookApp Team");

        mailSender.send(message);
    }

    // Password Change Email
    public void sendPasswordChangeEmail(String toEmail, String subject, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("divyanshshuklaofficial23@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText("Your new password is: " + newPassword + "\n\nPlease keep it safe.");

        mailSender.send(message);
    }
}
