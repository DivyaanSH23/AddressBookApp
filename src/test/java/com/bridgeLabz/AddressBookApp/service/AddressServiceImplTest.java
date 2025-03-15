package com.bridgeLabz.AddressBookApp.service;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.model.Address;
import com.bridgeLabz.AddressBookApp.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setId(1L);
        address.setName("John Doe");
        address.setCity("New York");
        address.setPhoneNumber("9876543210");

        addressDTO = new AddressDTO();
        addressDTO.setName("John Doe");
        addressDTO.setCity("New York");
        addressDTO.setPhoneNumber("9876543210");
    }

    // ✅ Test getAllAddresses()
    @Test
    void testGetAllAddresses_Success() {
        when(addressRepository.findAll()).thenReturn(Arrays.asList(address));
        List<Address> addresses = addressService.getAllAddresses();
        assertFalse(addresses.isEmpty());
    }

    @Test
    void testGetAllAddresses_ThrowsException() {
        when(addressRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> addressService.getAllAddresses());
    }

    // ✅ Test getAddressById()
    @Test
    void testGetAddressById_Success() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Address retrievedAddress = addressService.getAddressById(1L);
        assertNotNull(retrievedAddress);
    }

    @Test
    void testGetAddressById_ThrowsException() {
        when(addressRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> addressService.getAddressById(1L));
    }

    // ✅ Test createAddress()
    @Test
    void testCreateAddress_Success() {
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        Address createdAddress = addressService.createAddress(addressDTO);
        assertNotNull(createdAddress);
    }

    @Test
    void testCreateAddress_ThrowsException() {
        when(addressRepository.save(any(Address.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> addressService.createAddress(addressDTO));
    }

    // ✅ Test updateAddress()
    @Test
    void testUpdateAddress_Success() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Address updatedAddress = addressService.updateAddress(1L, addressDTO);
        assertNotNull(updatedAddress);
    }

    @Test
    void testUpdateAddress_ThrowsException() {
        when(addressRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> addressService.updateAddress(1L, addressDTO));
    }

    // ✅ Test deleteAddress()
    @Test
    void testDeleteAddress_Success() {
        when(addressRepository.existsById(1L)).thenReturn(true);
        doNothing().when(addressRepository).deleteById(1L);

        assertDoesNotThrow(() -> addressService.deleteAddress(1L));
    }

    @Test
    void testDeleteAddress_ThrowsException() {
        when(addressRepository.existsById(1L)).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> addressService.deleteAddress(1L));
    }
}
