package com.tbarauskas.parkingrestapi.entity.user;

import com.tbarauskas.parkingrestapi.dto.user.CreateUserRequestDTO;
import com.tbarauskas.parkingrestapi.dto.user.UpdateUserRequestDTO;
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

    public User(CreateUserRequestDTO createUserDTO) {
        this.username = createUserDTO.getUsername();
        this.password = createUserDTO.getPassword();
        this.name = createUserDTO.getName();
        this.surname = createUserDTO.getSurname();
        this.carNumber = createUserDTO.getCarNumber();
    }

    public User(UpdateUserRequestDTO updateUserDTO) {
        this.id = updateUserDTO.getId();
        this.username = updateUserDTO.getUsername();
        this.password = updateUserDTO.getPassword();
        this.name = updateUserDTO.getName();
        this.surname = updateUserDTO.getSurname();
        this.carNumber = updateUserDTO.getCarNumber();
    }
}
