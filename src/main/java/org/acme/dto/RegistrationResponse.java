package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.acme.constants.AccountRole;
import org.acme.model.Role;
import org.acme.model.UserProfiles;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class RegistrationResponse implements Serializable {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String dateOfBirth;
    private UserProfileDto profile;

    public RegistrationResponse(RegistrationRequest request) {
        this.email = request.getEmail();
        this.username = request.getUsername();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.profile = request.getProfile();
        this.dateOfBirth = request.getDateOfBirth();
    }


}
