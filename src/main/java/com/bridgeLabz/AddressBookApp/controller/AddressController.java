package com.bridgeLabz.AddressBookApp.controller;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.dto.ResponseDTO;
import com.bridgeLabz.AddressBookApp.interfaces.IAddressService; // Updated import ✅
import com.bridgeLabz.AddressBookApp.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    // GET all addresses
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllAddresses() {
        List<Address> addressList = addressService.getAllAddresses();
        return ResponseEntity.ok(new ResponseDTO("Fetched all addresses", addressList));
    }

    // GET address by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        if (address != null) {
            return ResponseEntity.ok(new ResponseDTO("Address found", address));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST - Create new address
    @PostMapping
    public ResponseEntity<ResponseDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        Address newAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(new ResponseDTO("Address added successfully", newAddress));
    }

    // PUT - Update existing address
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        Address updatedAddress = addressService.updateAddress(id, addressDTO);
        if (updatedAddress != null) {
            return ResponseEntity.ok(new ResponseDTO("Address updated successfully", updatedAddress));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Remove address
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok(new ResponseDTO("Address deleted successfully", null));
    }
}
