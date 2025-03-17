package com.example.AddressBookManagement.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "First letter must be uppercase")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "First letter must be uppercase")
    private String lastName;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

}

