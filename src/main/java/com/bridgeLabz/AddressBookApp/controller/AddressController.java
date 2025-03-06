package com.bridgeLabz.AddressBookApp.controller;

import com.bridgeLabz.AddressBookApp.model.Address;
import com.bridgeLabz.AddressBookApp.repository.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressRepository addressRepository;

    public AddressController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // GET all addresses
    @GetMapping
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // GET address by ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - Create new address
    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressRepository.save(address);
    }

    // PUT - Update existing address
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    existingAddress.setName(updatedAddress.getName());
                    existingAddress.setCity(updatedAddress.getCity());
                    existingAddress.setPhoneNumber(updatedAddress.getPhoneNumber());
                    return ResponseEntity.ok(addressRepository.save(existingAddress));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE - Remove address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
