package com.example.contactslist.service;

import com.example.contactslist.dto.ContactDto;
import com.example.contactslist.entity.Contact;
import com.example.contactslist.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public ContactDto findById(long id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (contact == null) {
            throw new IllegalArgumentException("id NOT_FOUND");
        }
        return mapToDto(contact);
    }

    @Override
    public Collection<ContactDto> findAll() {
        return contactRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void save(ContactDto contactDto) {
        contactRepository.save(mapToEntity(contactDto));
    }

    @Override
    public void update(long id, ContactDto contactDto) {
        contactRepository.update(id, mapToEntity(contactDto));
    }

    @Override
    public void deleteById(long contactId) {
        contactRepository.deleteById(contactId);
    }

    public Contact mapToEntity(ContactDto contactDto) {
        return new Contact(
                contactDto.getId(),
                contactDto.getFirstName(),
                contactDto.getLastName(),
                contactDto.getEmail(),
                contactDto.getPhone()
        );
    }

    public ContactDto mapToDto(Contact contact) {
        return new ContactDto(
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhone()
        );
    }
}
