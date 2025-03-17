package com.example.AddressBookManagement.DTO;

import java.io.Serializable;

public class RabbitMQMessageDTO implements Serializable {
    private String name;
    private String city;
    private String phoneNumber;

    public RabbitMQMessageDTO() {}

    public RabbitMQMessageDTO(String name, String city, String phoneNumber) {
        this.name = name;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
