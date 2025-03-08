package com.bridgeLabz.AddressBookApp.service;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.interfaces.IAddressService;
import com.bridgeLabz.AddressBookApp.model.Address;
import com.bridgeLabz.AddressBookApp.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j // Lombok Logging ✅
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        log.info("Fetching all addresses from database...");
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        log.info("Fetching address with ID: {}", id);
        return addressRepository.findById(id).orElseGet(() -> {
            log.warn("Address with ID {} not found!", id);
            return null;
        });
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        log.info("Creating new address: {}", addressDTO);
        Address newAddress = new Address(addressDTO);
        Address savedAddress = addressRepository.save(newAddress);
        log.info("Address created successfully with ID: {}", savedAddress.getId());
        return savedAddress;
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        log.info("Updating address with ID: {}", id);
        return addressRepository.findById(id).map(existingAddress -> {
            existingAddress.setName(addressDTO.getName());
            existingAddress.setCity(addressDTO.getCity());
            existingAddress.setPhoneNumber(addressDTO.getPhoneNumber());
            Address updatedAddress = addressRepository.save(existingAddress);
            log.info("Updated address successfully: {}", updatedAddress);
            return updatedAddress;
        }).orElseGet(() -> {
            log.warn("Update failed. Address with ID {} not found!", id);
            return null;
        });
    }

    @Override
    public void deleteAddress(Long id) {
        log.info("Deleting address with ID: {}", id);
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
            log.info("Address with ID {} deleted successfully", id);
        } else {
            log.warn("Delete failed. Address with ID {} not found!", id);
        }
    }
}
