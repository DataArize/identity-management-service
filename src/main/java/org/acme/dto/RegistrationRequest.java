package org.acme.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationRequest {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String dateOfBirth;

}
