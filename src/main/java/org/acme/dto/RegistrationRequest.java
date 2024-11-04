package org.acme.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.acme.constants.DataValidation;
import org.acme.constants.ValidationErrors;

import java.io.Serializable;

@Data
public class RegistrationRequest implements Serializable {

    @NotBlank(message = ValidationErrors.EMAIL_IS_MANDATORY)
    @Email(message = ValidationErrors.INVALID_EMAIL_FORMAT)
    private String email;
    @NotBlank(message = ValidationErrors.USER_NAME_IS_MANDATORY)
    private String username;
    @NotBlank(message = ValidationErrors.FIRST_NAME_IS_MANDATORY)
    private String firstName;
    @NotBlank(message = ValidationErrors.LAST_NAME_IS_MANDATORY)
    private String lastName;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = DataValidation.PASSWORD_PATTERN,
            message = DataValidation.PASSWORD_REQUIREMENTS_MESSAGE)
    private String password;
    private String dateOfBirth;
    private UserProfileDto profile;

}
