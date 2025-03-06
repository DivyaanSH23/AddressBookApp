package com.bridgeLabz.AddressBookApp.service;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.interfaces.IAddressService; // Updated import ✅
import com.bridgeLabz.AddressBookApp.model.Address;
import com.bridgeLabz.AddressBookApp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.orElse(null);
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        Address newAddress = new Address(addressDTO);
        return addressRepository.save(newAddress);
    }


    }

