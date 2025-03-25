package com.example.AddressBookManagement.controller;

import com.example.AddressBookManagement.DTO.AddressDTO;
import com.example.AddressBookManagement.model.Address;
import com.example.AddressBookManagement.services.AddressServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/addresses")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Address Controller", description = "API for Address Management")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    // Remove RabbitMQProducer since it's no longer needed
    // @Autowired
    // private RabbitMQProducer rabbitMQProducer;

    @GetMapping
    @Operation(summary = "Get all addresses", description = "Fetches all addresses from the system")
    public ResponseEntity<List<Address>> getAllAddresses() {
        try {
            log.info("Fetching all addresses");
            List<Address> addresses = addressService.getAllAddresses();
            if (addresses.isEmpty()) {
                return ResponseEntity.noContent().build(); // ✅ Return 204 if no content
            }
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            log.error("Error fetching addresses", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get address by ID", description = "Fetches a specific address using its ID")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        try {
            log.info("Fetching address with ID: {}", id);
            Optional<Address> address = addressService.getAddressById(id);
            return address.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build()); // ✅ Return 404 without message
        } catch (IllegalArgumentException e) {
            log.error("Invalid ID format: {}", id, e);
            return ResponseEntity.badRequest().build(); // ✅ Handle invalid ID
        } catch (Exception e) {
            log.error("Error fetching address ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new address", description = "Stores a new address in the system")
    public ResponseEntity<Address> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        try {
            log.info("Creating new address: {}", addressDTO);
            Address newAddress = addressService.createAddress(addressDTO);
            return ResponseEntity.status(201).body(newAddress); // ✅ Return 201 Created
        } catch (Exception e) {
            log.error("Error creating address", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an address", description = "Updates an existing address by ID")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO addressDTO) {
        try {
            log.info("Updating address with ID: {}", id);
            Address updatedAddress = addressService.updateAddress(id, addressDTO);
            if (updatedAddress == null) {
                return ResponseEntity.notFound().build(); // ✅ 404 for not found
            }
            return ResponseEntity.ok(updatedAddress);
        } catch (Exception e) {
            log.error("Error updating address ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an address", description = "Deletes an existing address by ID")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        try {
            log.info("Deleting address with ID: {}", id);
            addressService.deleteAddress(id);
            return ResponseEntity.noContent().build(); // ✅ 204 No Content
        } catch (IllegalArgumentException e) {
            log.error("Invalid ID format: {}", id, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error deleting address ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // Remove RabbitMQ-related endpoint
    // @PostMapping("/send")
    // @Operation(summary = "Send address to RabbitMQ", description = "Sends an address as a message to RabbitMQ queue")
    // public ResponseEntity<String> sendMessageToQueue(@RequestBody RabbitMQMessageDTO messageDTO) {
    //     try {
    //         log.info("Sending address data to RabbitMQ: {}", messageDTO);
    //         rabbitMQProducer.sendMessage(messageDTO);
    //         return ResponseEntity.ok("Message sent to RabbitMQ: " + messageDTO.getName());
    //     } catch (Exception e) {
    //         log.error("Error sending message to RabbitMQ", e);
    //         return ResponseEntity.internalServerError().build();
    // }
//}
}
