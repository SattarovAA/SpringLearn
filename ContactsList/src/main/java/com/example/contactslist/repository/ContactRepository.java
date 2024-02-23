package com.example.contactslist.repository;

import com.example.contactslist.entity.Contact;

import java.util.Collection;
import java.util.Optional;

public interface ContactRepository {
    Collection<Contact> findAll();

    Optional<Contact> findById(long id);

    Contact save(Contact contact);

    Contact update(long id, Contact contact);

    void deleteById(long contactId);
}
