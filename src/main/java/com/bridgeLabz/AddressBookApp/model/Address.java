package com.bridgeLabz.AddressBookApp.model;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    // Constructor to convert DTO to Model
    public Address(AddressDTO dto) {
        this.name = dto.getName();
        this.city = dto.getCity();
        this.phoneNumber = dto.getPhoneNumber();
    }
}
