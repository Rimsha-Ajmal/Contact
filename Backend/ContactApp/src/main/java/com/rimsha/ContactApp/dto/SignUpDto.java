package com.rimsha.ContactApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SignUpDto {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

}
