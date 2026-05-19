package com.example.sms.mapper;

import com.example.sms.dto.AddressDTO;
import com.example.sms.dto.UserDTO;
import com.example.sms.entity.Address;
import com.example.sms.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setActive(user.isActive());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        List<AddressDTO> addressDTOList = new ArrayList<>();
        if(user.getAddresses() != null) {
            user.getAddresses().forEach(address -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setStreet(address.getStreet());
                addressDTO.setCity(address.getCity());
                addressDTO.setState(address.getState());
                addressDTO.setZipCode(address.getZipCode());
                addressDTO.setCountry(address.getCountry());
                addressDTOList.add(addressDTO);
            });
        }
        userDTO.setAddressDTOList(addressDTOList);
        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setActive(userDTO.isActive());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());

        List<Address> addresses = new ArrayList<>();
        if(userDTO.getAddressDTOList() != null) {
            userDTO.getAddressDTOList().forEach(addressDTO -> {
                Address address = new Address();
                address.setStreet(addressDTO.getStreet());
                address.setCity(addressDTO.getCity());
                address.setState(addressDTO.getState());
                address.setZipCode(addressDTO.getZipCode());
                address.setCountry(addressDTO.getCountry());
                address.setUser(user);
                addresses.add(address);
            });
        }
        user.setAddresses(addresses);
        return user;
    }
}
