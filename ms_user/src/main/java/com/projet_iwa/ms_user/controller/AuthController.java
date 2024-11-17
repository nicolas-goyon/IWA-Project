package com.projet_iwa.ms_user.controller;


import com.projet_iwa.ms_user.Util;
import com.projet_iwa.ms_user.dto.UserDTO;
import com.projet_iwa.ms_user.model.AuthRequest;
import com.projet_iwa.ms_user.model.AuthResponse;
import com.projet_iwa.ms_user.model.User;
import com.projet_iwa.ms_user.service.UserService;
import com.projet_iwa.ms_user.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        // Vérifier les informations d'identification de l'utilisateur
        User existingUser = userService.findByUsername(authRequest.getUsername());

        if (existingUser != null && passwordEncoder.matches(authRequest.getPassword(), existingUser.getPassword())) {
            // Générer un JWT
            String token = jwtTokenService.generateToken(existingUser.getIduser());
            UserDTO user = Util.mapToUserDTO(existingUser);
            return new AuthResponse(token, user);
        }

        throw new RuntimeException("Invalid credentials"); // Lancer une exception ou gérer l'erreur selon le besoin
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
