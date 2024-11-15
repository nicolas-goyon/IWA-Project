package com.projet_iwa.ms_user;

import com.projet_iwa.ms_user.dto.UserDTO;
import com.projet_iwa.ms_user.model.User;

public class Util {
    // Méthode de mappage manuelle d'un User en UserDTO
    public static UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIduser(user.getIduser());
        userDTO.setUsername(user.getUsername());
        userDTO.setLastname(user.getLastname());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setBiographie((user.getBiographie()));
        userDTO.setRole((user.getRole()));
        return userDTO;
    }

}
