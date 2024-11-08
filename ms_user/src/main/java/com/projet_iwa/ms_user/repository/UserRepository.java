package com.projet_iwa.ms_user.repository;

import com.projet_iwa.ms_user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
