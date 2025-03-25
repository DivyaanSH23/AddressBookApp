package com.example.AddressBookManagement.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    // ✅ Name: Only letters and spaces
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    // ✅ Phone: Must be 10 digits
    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    // ✅ Email: Must follow standard email format
    @NotEmpty(message = "Email cannot be empty")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;

    // ✅ City: Only letters and spaces, no numbers
    @NotEmpty(message = "City cannot be empty")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "City must contain only letters and spaces")
    private String city;
}
