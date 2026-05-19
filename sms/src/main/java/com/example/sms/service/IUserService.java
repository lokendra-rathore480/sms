package com.example.sms.service;

import com.example.sms.dto.PaginationResponseDTO;
import com.example.sms.dto.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    PaginationResponseDTO<UserDTO> getAllUsers(int pageNo, int pageSize, String sortBy);

}
