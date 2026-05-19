package com.example.sms.service.impl;

import com.example.sms.dto.ContactDTO;
import com.example.sms.entity.Contact;
import com.example.sms.entity.User;
import com.example.sms.exception.NotFoundException;
import com.example.sms.mapper.ContactMapper;
import com.example.sms.repository.ContactRepository;
import com.example.sms.repository.UserRepository;
import com.example.sms.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        User user = userRepository.findById(contactDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + contactDTO.getUserId()));
        Contact contact = contactMapper.toEntity(contactDTO);
        contact.setCreatedAt(LocalDateTime.now());
        contact.setUpdatedAt(LocalDateTime.now());
        contact.setUser(user);
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toDTO(savedContact);
    }

    @Override
    public ContactDTO getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Contact not found with id: " + id));
        return contactMapper.toDTO(contact);
    }

    @Override
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
            Contact contact = contactRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Contact not found with id: " + id));
            if (!contact.getUser().getId().equals(contactDTO.getUserId())) {
                User user = userRepository.findById(contactDTO.getUserId())
                        .orElseThrow(() -> new NotFoundException("User not found with id: " + contactDTO.getUserId()));
                contact.setUser(user);
            }
            contact.setFirstName(contactDTO.getFirstName());
            contact.setLastName(contactDTO.getLastName());
            contact.setEmail(contactDTO.getEmail());
            contact.setPhoneNumber(contactDTO.getPhoneNumber());
            contact.setUpdatedAt(LocalDateTime.now());
            Contact updatedContact = contactRepository.save(contact);
            return contactMapper.toDTO(updatedContact);

    }

    @Override
    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found with id: " + id));
        contactRepository.delete(contact);
    }

    @Override
    public ContactDTO getContactByPhoneNumber(String phoneNumber) {
        Contact contact = contactRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("Contact not found with phone number: " + phoneNumber));
        return contactMapper.toDTO(contact);
    }
}
