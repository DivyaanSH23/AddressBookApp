package com.bridgeLabz.AddressBookApp.controller;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.dto.ResponseDTO;
import com.bridgeLabz.AddressBookApp.interfaces.IAddressService;
import com.bridgeLabz.AddressBookApp.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/addresses")
@Slf4j
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllAddresses() {
        log.info("Fetching all addresses...");
        List<Address> addressList = addressService.getAllAddresses();
        return ResponseEntity.ok(new ResponseDTO("Fetched all addresses", addressList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getAddressById(@PathVariable Long id) {
        log.info("Fetching address with ID: {}", id);
        Address address = addressService.getAddressById(id);
        if (address != null) {
            return ResponseEntity.ok(new ResponseDTO("Fetched address successfully", address));
        }
        log.warn("Address with ID {} not found", id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        log.info("Creating new address: {}", addressDTO);
        Address newAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(new ResponseDTO("Address created successfully", newAddress));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO addressDTO) {
        log.info("Updating address with ID {}: {}", id, addressDTO);
        Address updatedAddress = addressService.updateAddress(id, addressDTO);
        if (updatedAddress != null) {
            return ResponseEntity.ok(new ResponseDTO("Address updated successfully", updatedAddress));
        }
        log.warn("Failed to update. Address with ID {} not found", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteAddress(@PathVariable Long id) {
        log.info("Deleting address with ID: {}", id);
        if (addressService.getAddressById(id) != null) {
            addressService.deleteAddress(id);
            log.info("Deleted address with ID: {}", id);
            return ResponseEntity.ok(new ResponseDTO("Address deleted successfully", null));
        }
        log.warn("Failed to delete. Address with ID {} not found", id);
        return ResponseEntity.notFound().build();
    }
}
