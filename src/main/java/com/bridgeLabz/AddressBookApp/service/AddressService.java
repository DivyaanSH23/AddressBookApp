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
        log.info("Retrieving all addresses from database...");
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        log.info("Retrieving address with ID: {}", id);
        Optional<Address> address = addressRepository.findById(id);
        return address.orElse(null);
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        log.info("Saving new address: {}", addressDTO);
        Address newAddress = new Address(addressDTO);
        return addressRepository.save(newAddress);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        log.info("Updating address with ID {}: {}", id, addressDTO);
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            address.setName(addressDTO.getName());
            address.setCity(addressDTO.getCity());
            address.setPhoneNumber(addressDTO.getPhoneNumber());
            return addressRepository.save(address);
        }
        log.warn("Update failed. Address with ID {} not found", id);
        return null;
    }

    @Override
    public void deleteAddress(Long id) {
        log.info("Deleting address with ID: {}", id);
        addressRepository.deleteById(id);
    }
}
