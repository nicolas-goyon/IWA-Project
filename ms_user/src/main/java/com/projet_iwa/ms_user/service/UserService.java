package com.projet_iwa.ms_user.service;

import com.projet_iwa.ms_user.dto.UserDTO;
import com.projet_iwa.ms_user.model.User;
import com.projet_iwa.ms_user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.projet_iwa.ms_user.Util;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
         return users.stream()
                .map(Util::mapToUserDTO) // Utilisation de la méthode de mappage
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        // Utilisation de la méthode de mappage
        return Util.mapToUserDTO(user);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    // utilisé pour login
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
