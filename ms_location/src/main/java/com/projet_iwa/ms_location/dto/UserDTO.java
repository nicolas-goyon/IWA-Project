package com.projet_iwa.ms_user.dto;

public class UserDTO {

    private Long iduser;
    private String role;
    private String username;
    private String lastname;
    private String name;
    private String email;

    public UserDTO() {}

    public UserDTO(Long iduser, String role, String username, String lastname, String name, String email) {
        this.iduser = iduser;
        this.role = role;
        this.username = username;
        this.lastname = lastname;
        this.name = name;
        this.email = email;
    }

    // Getters et Setters
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
