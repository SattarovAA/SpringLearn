package org.example;

import org.example.entity.Contact;
import org.example.util.ContactParser;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;

@Component
public class ContactService {
    private final ContactStorage storage;

    public ContactService(ContactStorage storage) {
        this.storage = storage;
    }

    public void printAll() {
        storage.findAll().forEach(System.out::println);
    }

    public void addContact(String line) {
        Contact contact = ContactParser.parseLine(line);
        storage.save(contact);
    }

    public void deleteContact(String email) {
        Optional<Contact> contact = storage.findByEmail(email);
        if (contact.isEmpty()) {
            throw new IllegalArgumentException("Contact not found");
        }
        storage.delete(contact.get());
    }

    public void saveContacts(String outputPath) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(outputPath)) {
            storage.findAll().forEach(c ->
                    pw.println(String.join(";",
                            c.fullName(), c.phoneNumber(), c.email()))
            );
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File Not Found\npath:" + outputPath);
        }
    }
}