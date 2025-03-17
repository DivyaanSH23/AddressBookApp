package com.example.AddressBookManagement.repository;

import com.example.AddressBookManagement.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
