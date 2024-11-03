package org.acme.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.acme.constants.AccountRole;
import org.acme.dto.RegistrationRequest;
import org.acme.dto.UserProfileDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(schema = "enterprise")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends PanacheEntity {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String keycloakUserId;
    private LocalDate dateOfBirth;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST}, targetEntity = UserProfiles.class)
    private List<UserProfiles> userProfiles = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST}, targetEntity = UserSessions.class)
    private List<UserSessions> userSessions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_mappings",
            schema = "enterprise",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public User(RegistrationRequest request, String keycloakUserId) {
        this.email = request.getEmail();
        this.username = request.getUsername();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.keycloakUserId = keycloakUserId;
        this.userProfiles.add(new UserProfiles(request.getProfile()));
        this.roles.add(new Role(AccountRole.USER.name()));
    }
}
