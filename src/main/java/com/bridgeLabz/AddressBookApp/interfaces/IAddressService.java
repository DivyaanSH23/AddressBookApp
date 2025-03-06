package com.bridgeLabz.AddressBookApp.interfaces;

import com.bridgeLabz.AddressBookApp.dto.AddressDTO;
import com.bridgeLabz.AddressBookApp.model.Address;
import java.util.List;

public interface IAddressService {
    List<Address> getAllAddresses();
    Address getAddressById(Long id);
    Address createAddress(AddressDTO addressDTO);

}
