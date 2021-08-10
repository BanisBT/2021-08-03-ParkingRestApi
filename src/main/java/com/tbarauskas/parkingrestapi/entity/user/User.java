package com.tbarauskas.parkingrestapi.entity.user;

import com.tbarauskas.parkingrestapi.dto.user.CreateUserRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "carNumber")
    private String carNumber;

    @Column(name = "balance")
    private BigDecimal balance;

    @Transient
    private UserRole role;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    public User(CreateUserRequestDTO createUserRequestDTO) {
        this.username = createUserRequestDTO.getUsername();
        this.password = createUserRequestDTO.getPassword();
        this.name = createUserRequestDTO.getName();
        this.surname = createUserRequestDTO.getSurname();
        this.carNumber = createUserRequestDTO.getCarNumber();
    }
}
