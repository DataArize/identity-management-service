package org.acme.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "enterprise")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserSessions extends PanacheEntity {

    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String ipAddress;
    private String deviceInfo;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
}
