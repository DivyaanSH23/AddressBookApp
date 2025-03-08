package com.bridgeLabz.AddressBookApp.controller;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.interfaces.IAddressService;
import com.bridgeLabz.AddressBookApp.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@Slf4j // Lombok Logging ✅
public class AddressController {

    @Autowired
    private IAddressService addressService;

    // GET all addresses
    @GetMapping
    public List<Address> getAllAddresses() {
        log.info("Fetching all addresses...");
        return addressService.getAllAddresses();
    }

    // GET address by ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        log.info("Fetching address with ID: {}", id);
        Address address = addressService.getAddressById(id);
        if (address != null) {
            return ResponseEntity.ok(address);
        }
        log.warn("Address with ID {} not found", id);
        return ResponseEntity.notFound().build();
    }

    // POST - Create new address
    @PostMapping
    public Address createAddress(@RequestBody AddressDTO addressDTO) {
        log.info("Creating new address: {}", addressDTO);
        return addressService.createAddress(addressDTO);
    }

    // PUT - Update existing address
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        log.info("Updating address with ID {}: {}", id, addressDTO);
        Address updatedAddress = addressService.updateAddress(id, addressDTO);
        if (updatedAddress != null) {
            return ResponseEntity.ok(updatedAddress);
        }
        log.warn("Failed to update. Address with ID {} not found", id);
        return ResponseEntity.notFound().build();
    }

    // DELETE - Remove address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.info("Deleting address with ID: {}", id);
        if (addressService.getAddressById(id) != null) {
            addressService.deleteAddress(id);
            log.info("Deleted address with ID: {}", id);
            return ResponseEntity.noContent().build();
        }
        log.warn("Failed to delete. Address with ID {} not found", id);
        return ResponseEntity.notFound().build();
    }
}
