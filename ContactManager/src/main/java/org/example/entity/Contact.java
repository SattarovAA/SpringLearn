package org.example.entity;

public record Contact(String fullName, String phoneNumber, String email) {
    @Override
    public String toString() {
        return fullName.concat("|")
                .concat(phoneNumber).concat("|")
                .concat(email);
    }
}