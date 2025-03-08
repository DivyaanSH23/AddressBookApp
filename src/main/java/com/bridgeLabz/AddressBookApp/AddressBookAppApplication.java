package com.bridgeLabz.AddressBookApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AddressBookAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressBookAppApplication.class, args);
	}

}

//curl -X GET http://localhost:8080/addresses -H "Content-Type: application/json"
//curl -X GET http://localhost:8080/addresses/<ID> -H "Content-Type: application/json"
//curl -X POST http://localhost:8080/addresses \
//     -H "Content-Type: application/json" \
//     -d '{"name": "John Doe", "city": "Lucknow", "phoneNumber": "9876543210"}'
//curl -X PUT http://localhost:8080/addresses/<ID> \
//     -H "Content-Type: application/json" \
//     -d '{"name": "Jane Doe", "city": "Delhi", "phoneNumber": "9876543210"}'
//curl -X DELETE http://localhost:8080/addresses/<ID>
