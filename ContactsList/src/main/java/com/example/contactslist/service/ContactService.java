package com.example.contactslist.service;

import com.example.contactslist.dto.ContactDto;

import java.util.Collection;

public interface ContactService {
    ContactDto findById(long id);

    Collection<ContactDto> findAll();

    void save(ContactDto contactDto);

    void update(long id, ContactDto contactDto);

    void deleteById(long contactId);
}
