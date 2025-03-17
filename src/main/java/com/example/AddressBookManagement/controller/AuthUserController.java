package com.example.AddressBookManagement.controller;

import com.example.AddressBookManagement.DTO.AuthUserDTO;
import com.example.AddressBookManagement.DTO.LoginDTO;
import com.example.AddressBookManagement.DTO.ResetPasswordDTO;
import com.example.AddressBookManagement.services.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    private final AuthUserService authUserService;

    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthUserDTO userDTO) {
        return ResponseEntity.status(201).body(authUserService.registerUser(userDTO));
    }

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authUserService.loginUser(loginDTO));
    }

    @Operation(summary = "Forgot Password")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody LoginDTO loginDTO) {  // ✅ Accepting JSON request
        return ResponseEntity.ok(authUserService.forgotPassword(loginDTO.getEmail()));
    }

    @Operation(summary = "Reset Password")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResponseEntity.ok(authUserService.resetPassword(
                resetPasswordDTO.getToken(),
                resetPasswordDTO
        ));
    }
}
