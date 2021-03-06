package com.tbarauskas.parkingrestapi.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tbarauskas.parkingrestapi.dto.user.TokenUserDTO;
import com.tbarauskas.parkingrestapi.dto.user.UserRequestDTO;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tbarauskas.parkingrestapi.model.UserRoleName.MANAGER;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<UserRole> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ParkingTicket> tickets;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ParkingFine> fines;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    public User(UserRequestDTO createUserDTO) {
        this.username = createUserDTO.getUsername();
        this.password = createUserDTO.getPassword();
        this.name = createUserDTO.getName();
        this.surname = createUserDTO.getSurname();
        this.carNumber = createUserDTO.getCarNumber();
    }

    public User(TokenUserDTO tokenUserDTO) {
        this.id = tokenUserDTO.getId();
        this.username = tokenUserDTO.getUsername();
        this.carNumber = tokenUserDTO.getCarNumber();
        this.name = tokenUserDTO.getName();
        this.surname = tokenUserDTO.getSurname();
        this.roles = tokenUserDTO.getRoles();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isManager() {
        return roles.stream()
                .map(UserRole::getUserRole)
                .collect(Collectors.toSet())
                .contains(MANAGER.name());
    }
}
