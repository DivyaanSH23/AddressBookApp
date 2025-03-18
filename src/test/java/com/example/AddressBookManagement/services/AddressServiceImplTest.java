package com.example.AddressBookManagement.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.AddressBookManagement.DTO.AddressDTO;
import com.example.AddressBookManagement.model.Address;
import com.example.AddressBookManagement.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        address = new Address(1L, "John Doe", "1234567890", "john@example.com", "New York");
        addressDTO = new AddressDTO("John Doe", "1234567890", "john@example.com", "New York");
    }

    @Test
    void testGetAllAddressesTrue() {
        when(addressRepository.findAll()).thenReturn(List.of(address));
        assertTrue(addressService.getAllAddresses().size() > 0);
    }

    @Test
    void testGetAllAddressesFalse() {
        when(addressRepository.findAll()).thenReturn(List.of());
        assertFalse(addressService.getAllAddresses().size() > 0);
    }

    @Test
    void testGetAllAddressesThrows() {
        when(addressRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> addressService.getAllAddresses());
    }

    @Test
    void testGetAddressByIdTrue() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        assertTrue(addressService.getAddressById(1L).isPresent());
    }

    @Test
    void testGetAddressByIdFalse() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> addressService.getAddressById(1L));
    }


    @Test
    void testGetAddressByIdThrows() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> addressService.getAddressById(1L));
    }

    @Test
    void testCreateAddressTrue() {
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        assertTrue(addressService.createAddress(addressDTO) != null);
    }

    @Test
    void testCreateAddressFalse() {
        when(addressRepository.save(any(Address.class))).thenReturn(null);
        assertFalse(addressService.createAddress(addressDTO) != null);
    }

    @Test
    void testCreateAddressThrows() {
        when(addressRepository.save(any(Address.class))).thenThrow(new RuntimeException("Save error"));
        assertThrows(RuntimeException.class, () -> addressService.createAddress(addressDTO));
    }

    @Test
    void testUpdateAddressTrue() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        assertTrue(addressService.updateAddress(1L, addressDTO) != null);
    }

    @Test
    void testUpdateAddressFalse() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> addressService.updateAddress(1L, addressDTO));
    }

    @Test
    void testUpdateAddressThrows() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenThrow(new RuntimeException("Update error"));
        assertThrows(RuntimeException.class, () -> addressService.updateAddress(1L, addressDTO));
    }

    @Test
    void testDeleteAddressTrue() {
        when(addressRepository.existsById(1L)).thenReturn(true);
        doNothing().when(addressRepository).deleteById(1L);
        assertDoesNotThrow(() -> addressService.deleteAddress(1L));
    }

    @Test
    void testDeleteAddressFalse() {
        when(addressRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> addressService.deleteAddress(1L));
    }

    @Test
    void testDeleteAddressThrows() {
        when(addressRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Delete error")).when(addressRepository).deleteById(1L);
        assertThrows(RuntimeException.class, () -> addressService.deleteAddress(1L));
    }
}
