package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Boolean enabled;
    private Role role;

    @Transient
    private String passwordConfirmation;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<DeliveryAddress> deliveryAddresses = new HashSet<>(); // TODO: 2019-10-22 remove all hash set ???

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private Set<Cart> carts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(enabled, user.enabled) &&
                role == user.role &&
                Objects.equals(passwordConfirmation, user.passwordConfirmation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, surname, email, password, enabled, role, passwordConfirmation);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", role=" + role +
                '}';
    }
}