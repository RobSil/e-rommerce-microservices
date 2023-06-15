package com.robsil.erommerce.protoservice.util.model;

import java.util.List;
import java.util.Objects;


public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private Boolean emailConfirmed;
    private String password;
    private Boolean isEnabled;
    private List<String> roles;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public User() {}

    public User(Long id, String firstName, String lastName, String gender, String email, Boolean emailConfirmed, String password, Boolean isEnabled, List<String> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.emailConfirmed = emailConfirmed;
        this.password = password;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender) && Objects.equals(email, user.email) && Objects.equals(emailConfirmed, user.emailConfirmed) && Objects.equals(password, user.password) && Objects.equals(isEnabled, user.isEnabled) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, gender, email, emailConfirmed, password, isEnabled, roles);
    }
}
