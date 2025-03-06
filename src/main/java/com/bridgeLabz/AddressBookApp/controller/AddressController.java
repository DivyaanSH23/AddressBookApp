package com.bridgeLabz.AddressBookApp.controller;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.dto.ResponseDTO;
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
    public ResponseEntity<ResponseDTO> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return ResponseEntity.ok(new ResponseDTO("Fetched all addresses", addresses));
    }

    // GET address by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getAddressById(@PathVariable Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.map(value -> ResponseEntity.ok(new ResponseDTO("Address found", value)))
                .orElseGet(() -> ResponseEntity.ok(new ResponseDTO("Address not found", null)));
    }

    // POST - Create new address (without DTO)
    @PostMapping
    public ResponseEntity<ResponseDTO> createAddress(@RequestBody Address address) {
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.ok(new ResponseDTO("Address created successfully", savedAddress));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    existingAddress.setName(updatedAddress.getName());
                    existingAddress.setCity(updatedAddress.getCity());
                    existingAddress.setPhoneNumber(updatedAddress.getPhoneNumber());
                    Address savedAddress = addressRepository.save(existingAddress);
                    return ResponseEntity.ok(new ResponseDTO("Address updated successfully", savedAddress));
                })
                .orElseGet(() -> ResponseEntity.ok(new ResponseDTO("Address not found", null)));
    }

    // DELETE - Remove address
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteAddress(@PathVariable Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            return ResponseEntity.ok(new ResponseDTO("Address deleted successfully", null));
        } else {
            return ResponseEntity.ok(new ResponseDTO("Address not found", null));
        }
    }

    // POST - Create new address using DTO
    @PostMapping("/dto")
    public ResponseEntity<ResponseDTO> createAddressUsingDTO(@RequestBody AddressDTO addressDTO) {
        Address newAddress = new Address(addressDTO);
        Address savedAddress = addressRepository.save(newAddress);
        return ResponseEntity.ok(new ResponseDTO("Address created successfully using DTO", savedAddress));
    }

    // PUT - Update existing address using DTO
    @PutMapping("/dto/{id}")
    public ResponseEntity<ResponseDTO> updateAddressUsingDTO(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    existingAddress.setName(addressDTO.getName());
                    existingAddress.setCity(addressDTO.getCity());
                    existingAddress.setPhoneNumber(addressDTO.getPhoneNumber());
                    Address savedAddress = addressRepository.save(existingAddress);
                    return ResponseEntity.ok(new ResponseDTO("Address updated successfully using DTO", savedAddress));
                })
                .orElseGet(() -> ResponseEntity.ok(new ResponseDTO("Address not found", null)));
    }
}
