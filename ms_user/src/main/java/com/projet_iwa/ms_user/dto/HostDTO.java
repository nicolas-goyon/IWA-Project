package com.projet_iwa.ms_user.dto;

public class HostDTO {
    private String phone;
    private String username;
    private String lastname;
    private String name;
    private String email;
    private String biographie;

    public HostDTO() {}

    public HostDTO(String username, String lastname, String name, String email, String phone, String biographie) {
        this.username = username;
        this.lastname = lastname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.biographie = biographie;
    }

    // Getters et Setters


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

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }
}



