package com.tbarauskas.parkingrestapi.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String userRole;

    @Transient
    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @Transient
    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    @Override
    public String getAuthority() {
        return "ROLE_" + userRole;
    }
}
