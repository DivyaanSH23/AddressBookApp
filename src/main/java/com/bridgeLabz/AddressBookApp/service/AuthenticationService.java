package com.bridgeLabz.AddressBookApp.service;

import com.bridgeLabz.AddressBookApp.dto.AuthUserDTO;
import com.bridgeLabz.AddressBookApp.model.AuthUser;
import com.bridgeLabz.AddressBookApp.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    // Register New User
    public AuthUser register(AuthUserDTO userDTO) throws Exception {
        AuthUser user = new AuthUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        AuthUser savedUser = authUserRepository.save(user);

        // Send email after successful registration
        mailService.sendRegistrationEmail(savedUser.getEmail(), savedUser.getFirstName());

        return savedUser;
    }

    // Forgot Password
    public String forgotPassword(String email, String newPassword) {
        Optional<AuthUser> user = authUserRepository.findByEmail(email);
        if (user.isPresent()) {
            AuthUser existingUser = user.get();
            existingUser.setPassword(passwordEncoder.encode(newPassword));
            authUserRepository.save(existingUser);

            // Send confirmation email
            mailService.sendPasswordChangeEmail(existingUser.getEmail(), "Forgot Password", newPassword);

            return "Password successfully updated.";
        } else {
            return "User not found.";
        }
    }

    // Reset Password
    public String resetPassword(String email, String currentPassword, String newPassword) {
        Optional<AuthUser> user = authUserRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(currentPassword, user.get().getPassword())) {
            AuthUser existingUser = user.get();
            existingUser.setPassword(passwordEncoder.encode(newPassword));
            authUserRepository.save(existingUser);

            // Send confirmation email
            mailService.sendPasswordChangeEmail(existingUser.getEmail(), "Password Reset", newPassword);

            return "Password successfully reset.";
        } else {
            return "Invalid credentials or user not found.";
        }
    }
}
