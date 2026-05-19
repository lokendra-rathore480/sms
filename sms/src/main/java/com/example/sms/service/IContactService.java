package com.example.sms.service;

import com.example.sms.dto.ContactDTO;

public interface IContactService {

    ContactDTO createContact(ContactDTO contactDTO);
    ContactDTO getContactById(Long id);
    ContactDTO updateContact(Long id, ContactDTO contactDTO);
    void deleteContact(Long id);
    ContactDTO getContactByPhoneNumber(String phoneNumber);
}
