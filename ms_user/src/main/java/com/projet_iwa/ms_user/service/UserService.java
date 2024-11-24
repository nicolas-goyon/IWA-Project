package com.projet_iwa.ms_user.service;

import com.projet_iwa.ms_user.dto.HostDTO;
import com.projet_iwa.ms_user.dto.UserDTO;
import com.projet_iwa.ms_user.model.User;
import com.projet_iwa.ms_user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.projet_iwa.ms_user.Util;

import java.util.List;
import java.util.Optional;
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

    public HostDTO getHostById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        // Utilisation de la méthode de mappage
        return Util.mapToHostDTO(user);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public boolean updateUser(Long id, UserDTO userDTO) {
        // Vérifier si l'utilisateur existe
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return false; // L'utilisateur n'existe pas
        }

        User existingUser = optionalUser.get();

        // Conserver le mot de passe existant
        String existingPassword = existingUser.getPassword();

        // Transformer UserDTO en User et mettre à jour les champs
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setUsername(userDTO.getUsername());
        existingUser.setLastname(userDTO.getLastname());
        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setBiographie(userDTO.getBiographie());
        existingUser.setRole(userDTO.getRole());

        // Conserver le mot de passe existant
        existingUser.setPassword(existingPassword);

        // Sauvegarder l'utilisateur mis à jour
        userRepository.save(existingUser);

        return true;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    // utilisé pour login
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
