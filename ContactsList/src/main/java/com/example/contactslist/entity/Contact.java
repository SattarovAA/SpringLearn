package com.example.contactslist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public final class Contact {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
