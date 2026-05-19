package com.example.sms.mapper;

import com.example.sms.dto.ContactDTO;
import com.example.sms.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactDTO toDTO(Contact contact) {
        if (contact == null) {
            return null;
        }
        ContactDTO dto = new ContactDTO();
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setEmail(contact.getEmail());
        dto.setPhoneNumber(contact.getPhoneNumber());
        dto.setCreatedAt(contact.getCreatedAt());
        dto.setUpdatedAt(contact.getUpdatedAt());
        if(contact.getUser() != null) {
            dto.setUserId(contact.getUser().getId());
        }
        return dto;
    }

    public Contact toEntity(ContactDTO contactDTO) {
        if (contactDTO == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        return contact;
    }

}
