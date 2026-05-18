package com.example.sms.service.impl;

import com.example.sms.dto.UserDTO;
import com.example.sms.entity.User;
import com.example.sms.exception.AlreadyExistException;
import com.example.sms.exception.NotFoundException;
import com.example.sms.mapper.UserMapper;
import com.example.sms.repository.UserRepository;
import com.example.sms.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating user with username: {}", userDTO.getUsername());
        boolean exists = userRepository.existsByUsername(userDTO.getUsername());
        if (exists) {
            throw new AlreadyExistException("Username already exists");
        }
        if(userRepository.existsByEmail(userDTO.getEmail())) {

            throw new AlreadyExistException("Email already exists");
        }
        User user = userMapper.toEntity(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found with id: " + id));
            if (!user.getUsername().equals(userDTO.getUsername()) && userRepository.existsByUsername(userDTO.getUsername())){
                throw new AlreadyExistException("Username already exists");
            }
            if (!user.getEmail().equals(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
                throw new AlreadyExistException("Email already exists");
            }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found with id: " + id));
        userRepository.delete(user);

    }
}
