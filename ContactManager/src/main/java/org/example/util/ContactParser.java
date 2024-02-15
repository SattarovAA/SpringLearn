package org.example.util;

import org.example.entity.Contact;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ContactParser {
    public static final String ADD_COMMAND_EXAMPLE = "Correct format:\n" +
            "add Иванов Иван Иванович;+7123456789;someEmail@example.maxSix";
    private static final String NAME_REGEX =
            "([a-zA-Zа-яА-ЯёЁ]+\\s){2}[a-zA-Zа-яА-ЯёЁ]+";
    private static final String PHONE_REGEX =
            "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private static final String EMAIL_REGEX =
            "[a-zA-Z\\d._-]+@[a-zA-Z\\d._-]+\\.[a-zA-Z\\d_-]{2,6}";

    public static Contact parseLine(String line) {
        final int INDEX_NAME = 0;
        final int INDEX_PHONE = 1;
        final int INDEX_EMAIL = 2;
        String[] values = line.split(";");

        isCorrectLengthAddCommand(values.length);
        isCorrectName(values[INDEX_NAME]);
        isCorrectPhone(values[INDEX_PHONE]);
        isCorrectEmail(values[INDEX_EMAIL]);

        return new Contact(
                values[INDEX_NAME],
                values[INDEX_PHONE].replaceAll("[^+\\d]", ""),
                values[INDEX_EMAIL]);
    }

    public static void isCorrectLengthAddCommand(int length) {
        if (length != 3) {
            throw new IllegalArgumentException("Wrong format. Found " +
                    length + " argument.\n"
                    + ADD_COMMAND_EXAMPLE);
        }
    }

    public static void isCorrectName(String name) {
        if (!name.matches(NAME_REGEX)) {
            throw new IllegalArgumentException("Wrong name format. " + ADD_COMMAND_EXAMPLE);
        }
    }

    public static void isCorrectPhone(String phone) {
        if (!phone.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Wrong phone format. " + ADD_COMMAND_EXAMPLE);
        }
    }

    public static void isCorrectEmail(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Wrong email format. " + ADD_COMMAND_EXAMPLE);
        }
    }

    public static Map<String, Contact> readFile(String path) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(path));
        Map<String, Contact> contacts = new HashMap<>();
        String line = inputStream.readLine();
        while (line != null) {
            try {
                Contact contact = parseLine(line);
                contacts.put(contact.email(), contact);
                line = inputStream.readLine();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return contacts;
            }
        }
        return contacts;
    }
}