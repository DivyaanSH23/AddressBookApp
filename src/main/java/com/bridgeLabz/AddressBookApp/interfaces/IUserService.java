package com.bridgeLabz.AddressBookApp.interfaces;

import com.bridgeLabz.AddressBookApp.dto.AuthUserDTO;
import com.bridgeLabz.AddressBookApp.model.AuthUser;

public interface IUserService {
    AuthUser register(AuthUserDTO userDTO);
}
