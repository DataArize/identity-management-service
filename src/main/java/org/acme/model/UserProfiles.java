package org.acme.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.acme.dto.UserProfileDto;

@Data
@Entity
@Table(schema = "enterprise")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserProfiles extends PanacheEntity {

    private String profileName;
    private String avatarUrl;
    private int ageRestrictions;
    private String language;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    public UserProfiles(UserProfileDto userProfileDto) {
        this.profileName = userProfileDto.getProfileName();
        this.ageRestrictions = userProfileDto.getAgePreference();
        this.avatarUrl = userProfileDto.getAvatarUrl();
        this.language = userProfileDto.getLanguage();
    }
}
