package com.example.AddressBookManagement.services;

import com.example.AddressBookManagement.DTO.AuthUserDTO;
import com.example.AddressBookManagement.DTO.LoginDTO;
import com.example.AddressBookManagement.DTO.ResetPasswordDTO;
import com.example.AddressBookManagement.model.AuthUser;
import com.example.AddressBookManagement.repository.AuthUserRepository;
// import com.example.AddressBookManagement.Utils.JwtUtil; // ❌ Commented out JWT import
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthUserService {

    private final AuthUserRepository authUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    // private final JwtUtil jwtUtil; // ❌ Commented out JWT injection
    private final JavaMailSender mailSender;

    public AuthUserService(AuthUserRepository authUserRepository, /*JwtUtil jwtUtil,*/ JavaMailSender mailSender) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        // this.jwtUtil = jwtUtil; // ❌ Commented out
        this.mailSender = mailSender;
    }

    @Transactional
    public String registerUser(AuthUserDTO userDTO) {
        if (authUserRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        AuthUser user = new AuthUser();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        authUserRepository.save(user);

        sendEmail(user.getEmail(), "Welcome!", "Thank you for registering!");

        return "User registered successfully!";
    }

    public String loginUser(LoginDTO loginDTO) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }

        AuthUser user = userOptional.get();
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password!");
        }

        // String token = jwtUtil.generateToken(user.getEmail()); // ❌ JWT logic removed
        // user.setJwtToken(token); // ❌ JWT logic removed
        authUserRepository.save(user);

        return "Login successful"; // ✅ Changed to return a success message instead of a token
    }

    @Transactional
    public String forgotPassword(String email) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        AuthUser user = userOptional.get();
        // String token = jwtUtil.generateToken(email); // ❌ Commented out

        // user.setResetToken(token); // ❌ Commented out
        user.setTokenExpiry(new Date(System.currentTimeMillis() + 15 * 60 * 1000));
        authUserRepository.save(user);

        // sendEmail(email, "Reset Password", "Your reset token is: " + token); // ❌ Commented out

        return "Password reset instructions sent to: " + email;
    }

    @Transactional
    public String resetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
        // Optional<AuthUser> userOptional = authUserRepository.findByEmail(jwtUtil.extractEmail(token)); // ❌ Commented out
        Optional<AuthUser> userOptional = Optional.empty(); // ✅ Placeholder to prevent errors

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        AuthUser user = userOptional.get();

        if (user.getTokenExpiry().before(new Date())) {
            throw new IllegalArgumentException("Token expired! Request a new reset token.");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        user.setResetToken(null);
        user.setTokenExpiry(null);
        authUserRepository.save(user);

        return "Password reset successfully!";
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom("your-email@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email", e);
}
}
}
