package org.acme.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.acme.dto.RegistrationRequest;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "enterprise")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends PanacheEntity {

    private String email;
    private String name;
    private LocalDate dateOfBirth;
    private String profilePicture;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean isActive;

    public User(RegistrationRequest request) {
        this.email = request.getEmail();
        this.name = request.getUsername();
    }
}
