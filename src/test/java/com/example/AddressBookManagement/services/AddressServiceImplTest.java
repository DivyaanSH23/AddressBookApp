package com.example.AddressBookManagement.services;

import com.example.AddressBookManagement.DTO.AddressDTO;
import com.example.AddressBookManagement.model.Address;
import com.example.AddressBookManagement.repository.AddressRepository;
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
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        address = new Address(1L, "John Doe", "9876543210", "john@example.com", "New York");

        addressDTO = new AddressDTO();
        addressDTO.setName("John Doe");
        addressDTO.setPhone("9876543210");
        addressDTO.setEmail("john@example.com");
        addressDTO.setCity("New York");
    }

    // ✅ Test getAllAddresses()
    @Test
    void testGetAllAddresses_Success() {
        when(addressRepository.findAll()).thenReturn(Arrays.asList(address));

        List<Address> addresses = addressService.getAllAddresses();

        assertFalse(addresses.isEmpty(), "Address list should not be empty");
        assertEquals(1, addresses.size(), "Only one address should be present");
    }

    @Test
    void testGetAllAddresses_EmptyList() {
        when(addressRepository.findAll()).thenReturn(Arrays.asList());

        List<Address> addresses = addressService.getAllAddresses();

        assertTrue(addresses.isEmpty(), "Address list should be empty");
    }

    // ✅ Test getAddressById()
    @Test
    void testGetAddressById_Success() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        Optional<Address> retrievedAddress = addressService.getAddressById(1L);

        assertTrue(retrievedAddress.isPresent(), "Address should be found");
        assertEquals("John Doe", retrievedAddress.get().getName());
    }

    @Test
    void testGetAddressById_NotFound() {
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> addressService.getAddressById(99L));

        assertEquals("Address not found", exception.getMessage());
    }

    // ✅ Test createAddress()
    @Test
    void testCreateAddress_Success() {
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Address createdAddress = addressService.createAddress(addressDTO);

        assertNotNull(createdAddress, "Created address should not be null");
        assertEquals("John Doe", createdAddress.getName(), "Created name should match");
    }

    @Test
    void testCreateAddress_ThrowsException() {
        when(addressRepository.save(any(Address.class))).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> addressService.createAddress(addressDTO));

        assertEquals("Database error", exception.getMessage());
    }

    // ✅ Test updateAddress()
    @Test
    void testUpdateAddress_Success() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Address updatedAddress = addressService.updateAddress(1L, addressDTO);

        assertNotNull(updatedAddress, "Updated address should not be null");
        assertEquals("John Doe", updatedAddress.getName(), "Updated address name should match");
    }

    @Test
    void testUpdateAddress_NotFound() {
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> addressService.updateAddress(99L, addressDTO));

        assertEquals("Address not found", exception.getMessage());
    }

    // ✅ Test deleteAddress()
    @Test
    void testDeleteAddress_Success() {
        when(addressRepository.existsById(1L)).thenReturn(true);
        doNothing().when(addressRepository).deleteById(1L);

        assertDoesNotThrow(() -> addressService.deleteAddress(1L));
        verify(addressRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAddress_NotFound() {
        when(addressRepository.existsById(99L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> addressService.deleteAddress(99L));

        assertEquals("Address not found", exception.getMessage());
    }

    @Test
    void testDeleteAddress_ThrowsException() {
        doThrow(new RuntimeException("Address not found")).when(addressRepository).deleteById(99L);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            addressService.deleteAddress(99L);
        });

        assertEquals("Address not found", exception.getMessage());
    }

}
