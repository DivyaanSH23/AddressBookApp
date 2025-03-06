package com.bridgeLabz.AddressBookApp.service;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.interfaces.IAddressService; // Updated Interface Import ✅
import com.bridgeLabz.AddressBookApp.model.Address;
import com.bridgeLabz.AddressBookApp.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        Address newAddress = new Address(addressDTO);
        return addressRepository.save(newAddress);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        return addressRepository.findById(id).map(existingAddress -> {
            existingAddress.setName(addressDTO.getName());
            existingAddress.setCity(addressDTO.getCity());
            existingAddress.setPhoneNumber(addressDTO.getPhoneNumber());
            return addressRepository.save(existingAddress);
        }).orElse(null);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
