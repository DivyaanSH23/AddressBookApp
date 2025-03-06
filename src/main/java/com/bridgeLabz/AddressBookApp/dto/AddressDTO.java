package com.bridgeLabz.AddressBookApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String name;
    private String city;
    private String phoneNumber;
}
