package org.acme.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegistrationRequest implements Serializable {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String dateOfBirth;
    private UserProfileDto profile;

}
