package org.example;

import org.example.entity.Contact;
import org.example.util.ContactParser;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.example.config.AppConfig.NO_VALUE;

public class ContactStorage {
    private final Map<String, Contact> contacts;

    public ContactStorage(String path) {
        contacts = new HashMap<>();
        if (path.equals(NO_VALUE)) {
            System.out.println("No input path value in config");
            return;
        }
        try {
            contacts.putAll(ContactParser.readFile(path));
        } catch (IOException e) {
            System.err.println("Reading error initialization file.\n" +
                    "Incorrect path:" + path);
        }
    }

    public Optional<Contact> findByEmail(String email) {
        return Optional.ofNullable(contacts.get(email));
    }

    public Collection<Contact> findAll() {
        return contacts.values();
    }

    public void save(Contact contact) {
        contacts.put(contact.email(), contact);
    }

    public void delete(Contact contact) {
        contacts.remove(contact.email(), contact);
    }
}