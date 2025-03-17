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
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Za-z\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    private String phone;
    private String email;
    private String city;
}