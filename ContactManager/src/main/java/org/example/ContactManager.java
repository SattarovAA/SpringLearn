package org.example;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.example.util.ContactParser.ADD_COMMAND_EXAMPLE;
import static org.example.util.ContactParser.isCorrectLengthAddCommand;

@Component
public class ContactManager {
    private boolean isWorking = true;
    private final ContactService service;

    public ContactManager(ContactService service) {
        this.service = service;
    }

    public void readConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ADD_COMMAND_EXAMPLE +
                "\nprint\nsave [path]" +
                "\ndelete [email]\nexit");
        while (isWorking) {
            try {
                String command = scanner.nextLine();
                String[] tokens = command.split("\\s+", 2);
                switch (tokens[0]) {
                    case "exit" -> isWorking = false;
                    case "print" -> service.printAll();
                    case "add" -> {
                        if (command.length() < 4) {
                            isCorrectLengthAddCommand(0);
                        }
                        service.addContact(command.substring(4));
                        System.out.println("Success");
                    }
                    case "delete" -> {
                        String email = (tokens.length > 1) ? tokens[1] : null;
                        if (tokens.length == 1) {
                            System.out.print("Remote contact email: ");
                            email = scanner.nextLine();
                        }
                        service.deleteContact(email);
                        System.out.println("Success");
                    }
                    case "save" -> {
                        String path = (tokens.length > 1) ? tokens[1] : null;
                        if (tokens.length == 1) {
                            System.out.print("Enter output path: ");
                            path = scanner.nextLine();
                        }
                        service.saveContacts(path);
                        System.out.println("Success");
                    }
                    default -> System.out.println("unknown command:" + tokens[0]);
                }
            } catch (IllegalArgumentException | FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}