package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    public RegistrationResponse(String email, String username, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
