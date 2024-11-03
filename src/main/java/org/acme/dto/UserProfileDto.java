package org.acme.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserProfileDto implements Serializable {

    private String profileName;
    private String avatarUrl;
    private String language;
    private int agePreference;
}
