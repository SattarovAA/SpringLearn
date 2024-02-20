package com.example.studentsregistration.entity;

public record Student(Integer id, String firstName, String lastName, Integer age) {
    @Override
    public String toString() {
        return String.valueOf(id).concat("|")
                .concat(firstName).concat("|")
                .concat(lastName).concat("|")
                .concat(String.valueOf(age));
    }
}
