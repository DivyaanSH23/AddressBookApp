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
@Slf4j
public class AddressService implements IAddressService {

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
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address with ID " + id + " not found"));
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        log.info("Creating new address: {}", addressDTO);
        Address newAddress = new Address(addressDTO);
        return addressRepository.save(newAddress);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        log.info("Updating address with ID: {}", id);
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address with ID " + id + " not found"));

        existingAddress.setName(addressDTO.getName());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setPhoneNumber(addressDTO.getPhoneNumber());

        return addressRepository.save(existingAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        log.info("Deleting address with ID: {}", id);
        if (!addressRepository.existsById(id)) {
            throw new RuntimeException("Address with ID " + id + " not found");
        }
        addressRepository.deleteById(id);
    }
}
