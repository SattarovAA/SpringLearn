package com.example.contactslist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public final class ContactDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
