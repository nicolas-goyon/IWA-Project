package com.projet_iwa.ms_user.model;

import com.projet_iwa.ms_user.dto.UserDTO;

public class AuthResponse {
    private String token;
    private UserDTO user;

    public AuthResponse(String token, UserDTO user) {this.user = user; this.token = token; }

    public String getToken() { return token; }
    public UserDTO getUser() {
        return user;
    }

}