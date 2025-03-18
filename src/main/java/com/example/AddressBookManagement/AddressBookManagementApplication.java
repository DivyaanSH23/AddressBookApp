package com.example.AddressBookManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching  // ✅ Enables caching in your Spring Boot application
public class AddressBookManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(AddressBookManagementApplication.class, args);
	}
}
