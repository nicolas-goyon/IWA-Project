package com.projet_iwa.ms_user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private Long iduser;

    @Column(name = "role", nullable = false, length = 10) // Type d'utilisateur
    private String role;

    @Column(name = "username", nullable = false, length = 10) // Type d'utilisateur
    private String username;

    @Column(name = "lastname", nullable = false, length = 100) // Nom de famille
    private String lastname;

    @Column(name = "name", nullable = false, length = 100) // Prénom
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 100) // Email
    private String email;

    @Column(name = "phone", length = 15) // Numéro de téléphone
    private String phone;

    @Column(name = "password", nullable = false, length = 255) // Mot de passe
    private String password;


    @Column(name = "biographie", nullable = true, columnDefinition = "TEXT") // Mot de passe
    private String biographie;


    // Getters et Setters

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }
    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
