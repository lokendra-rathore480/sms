package com.example.sms.service.impl;

import com.example.sms.constant.ApplicationConstants;
import com.example.sms.dto.PaginationResponseDTO;
import com.example.sms.dto.UserDTO;
import com.example.sms.entity.User;
import com.example.sms.exception.AlreadyExistException;
import com.example.sms.exception.NotFoundException;
import com.example.sms.mapper.UserMapper;
import com.example.sms.repository.UserRepository;
import com.example.sms.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
            throw new AlreadyExistException(ApplicationConstants.USERNAME_ALREADY_EXIST);
        }
        if(userRepository.existsByEmail(userDTO.getEmail())) {

            throw new AlreadyExistException(ApplicationConstants.EMAIl_ALREADY_EXIST);
        }
        User user = userMapper.toEntity(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found with id: " + id));
            if (!user.getUsername().equals(userDTO.getUsername()) && userRepository.existsByUsername(userDTO.getUsername())){
                throw new AlreadyExistException(ApplicationConstants.USERNAME_ALREADY_EXIST);
            }
            if (!user.getEmail().equals(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
                throw new AlreadyExistException(ApplicationConstants.EMAIl_ALREADY_EXIST);
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

    @Override
    public PaginationResponseDTO<UserDTO> getAllUsers(int pageNo, int pageSize, String sortBy) {
        Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserDTO> userDTOList = userPage.getContent().stream().map(userMapper::toDTO)
                .toList();
        PaginationResponseDTO<UserDTO> paginationResponseDTO = new PaginationResponseDTO<>();
        paginationResponseDTO.setContent(userDTOList);
        paginationResponseDTO.setPageNumber(userPage.getNumber());
        paginationResponseDTO.setPageSize(userPage.getSize());
        paginationResponseDTO.setTotalElements(userPage.getTotalElements());
        paginationResponseDTO.setTotalPages(userPage.getTotalPages());
        paginationResponseDTO.setLast(userPage.isLast());
        paginationResponseDTO.setSortBy(sortBy);
        return paginationResponseDTO;

    }
}
