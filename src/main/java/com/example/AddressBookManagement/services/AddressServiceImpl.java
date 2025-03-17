package com.example.AddressBookManagement.services;

import com.example.AddressBookManagement.DTO.AddressDTO;
import com.example.AddressBookManagement.model.Address;
import com.example.AddressBookManagement.repository.AddressRepository;
import com.example.AddressBookManagement.interfaces.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        log.info("Fetching all addresses");
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        log.info("Fetching address with ID: {}", id);
        return addressRepository.findById(id)
                .or(() -> {
                    log.warn("Address with ID {} not found", id);
                    throw new RuntimeException("Address not found");
                });
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        log.info("Creating new address: {}", addressDTO);
        Address address = new Address(null, addressDTO.getName(), addressDTO.getPhone(), addressDTO.getEmail(), addressDTO.getCity());
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        log.info("Updating address with ID: {}", id);
        return addressRepository.findById(id)
                .map(address -> {
                    address.setName(addressDTO.getName());
                    address.setPhone(addressDTO.getPhone());
                    address.setEmail(addressDTO.getEmail());
                    address.setCity(addressDTO.getCity());
                    return addressRepository.save(address);
                })
                .orElseThrow(() -> {
                    log.error("Update failed: Address with ID {} not found", id);
                    return new RuntimeException("Address not found");
                });
    }

    @Override
    public void deleteAddress(Long id) {
        log.info("Deleting address with ID: {}", id);
        if (!addressRepository.existsById(id)) {
            log.error("Delete failed: Address with ID {} not found", id);
            throw new RuntimeException("Address not found");
        }
        addressRepository.deleteById(id);
    }
}
