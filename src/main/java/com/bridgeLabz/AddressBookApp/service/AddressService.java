package com.bridgeLabz.AddressBookApp.service;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.interfaces.IAddressService;
import com.bridgeLabz.AddressBookApp.model.Address;
import com.bridgeLabz.AddressBookApp.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        try {
            log.info("Fetching all addresses from database...");
            return addressRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching addresses: {}", e.getMessage());
            throw new RuntimeException("Error fetching addresses from database");
        }
    }

    @Override
    public Address getAddressById(Long id) {
        try {
            log.info("Fetching address with ID: {}", id);
            return addressRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Address with ID " + id + " not found"));
        } catch (Exception e) {
            log.error("Error fetching address: {}", e.getMessage());
            throw new RuntimeException("Error fetching address with ID " + id);
        }
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        try {
            log.info("Creating new address: {}", addressDTO);
            Address newAddress = new Address(addressDTO);
            return addressRepository.save(newAddress);
        } catch (Exception e) {
            log.error("Error creating address: {}", e.getMessage());
            throw new RuntimeException("Error creating address");
        }
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        try {
            log.info("Updating address with ID: {}", id);
            Address existingAddress = addressRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Address with ID " + id + " not found"));

            existingAddress.setName(addressDTO.getName());
            existingAddress.setCity(addressDTO.getCity());
            existingAddress.setPhoneNumber(addressDTO.getPhoneNumber());

            return addressRepository.save(existingAddress);
        } catch (Exception e) {
            log.error("Error updating address: {}", e.getMessage());
            throw new RuntimeException("Error updating address with ID " + id);
        }
    }

    @Override
    public void deleteAddress(Long id) {
        try {
            log.info("Deleting address with ID: {}", id);
            if (!addressRepository.existsById(id)) {
                log.warn("Address with ID {} not found!", id);
                throw new RuntimeException("Address with ID " + id + " not found");
            }
            addressRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting address: {}", e.getMessage());
            throw new RuntimeException("Error deleting address with ID " + id);
        }
    }
}
