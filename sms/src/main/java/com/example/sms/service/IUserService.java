package com.example.sms.service;

import com.example.sms.dto.UserDTO;

public interface IUserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO getUserById(Long id);
    void deleteUser(Long id);

}
