package com.robsil.erommerce.userentityservice.data.domain;

import com.robsil.userservice.data.domain.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String email;

    @Column(name = "email_confirmed")
    private boolean emailConfirmed;

    @Column
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isEnabled == user.isEnabled && Objects.equals(super.getId(), user.getId()) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(dateOfBirth, user.dateOfBirth) && gender == user.gender && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), firstName, lastName, dateOfBirth, gender, email, password, isEnabled, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles()
                .stream()
                .flatMap(role -> role.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.toString())))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
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

    public com.robsil.proto.User toProto() {
        com.robsil.proto.User.newBuilder().build();
        return com.robsil.proto.User.newBuilder()
                .setId(this.getId())
                .setFirstName(this.getFirstName())
                .setLastName(this.getLastName())
                .setGender(com.robsil.proto.Gender.valueOf(this.getGender().name()))
                .setEmail(this.getEmail())
                .setEmailConfirmed(this.isEmailConfirmed())
                .setPassword(this.getPassword())
                .setIsEnabled(this.isEnabled())
                .addAllRoles(this.getRoles()
                        .stream()
                        .map(role -> com.robsil.proto.ERole.valueOf(role.getCode()))
                        .collect(Collectors.toList()))
                .build();
    }
}
