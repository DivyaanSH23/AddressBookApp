package com.bridgeLabz.AddressBookApp.controller;

import com.bridgeLabz.AddressBookApp.dto.AuthUserDTO;
import com.bridgeLabz.AddressBookApp.dto.ResponseDTO;
import com.bridgeLabz.AddressBookApp.model.AuthUser;
import com.bridgeLabz.AddressBookApp.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody AuthUserDTO userDTO) {
        try {
            AuthUser user = authenticationService.register(userDTO);
            return new ResponseEntity<>(new ResponseDTO("User registered successfully! Check your email for confirmation.", user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Registration failed: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String email, @RequestBody Map<String, String> request) {
        String newPassword = request.get("password");
        String message = authenticationService.forgotPassword(email, newPassword);
        return new ResponseEntity<>(new ResponseDTO(message, null), HttpStatus.OK);
    }

    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<ResponseDTO> resetPassword(@PathVariable String email,
                                                     @RequestParam String currentPassword,
                                                     @RequestParam String newPassword) {
        String message = authenticationService.resetPassword(email, currentPassword, newPassword);
        return new ResponseEntity<>(new ResponseDTO(message, null), HttpStatus.OK);
    }
}
