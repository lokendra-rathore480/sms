package com.example.sms.controller;

import com.example.sms.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PostMapping
    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(Long id) {
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(Long id, UserDTO userDTO) {
        return null;
    }
}
