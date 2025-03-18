package com.example.AddressBookManagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {  // ✅ Implements Serializable
    private static final long serialVersionUID = 1L;  // ✅ Recommended for serialization

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private String city;
}
