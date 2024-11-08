package com.projet_iwa.ms_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUserApplication.class, args);
	}
	/*@GetMapping("/users")
    public String getUsers() {
        return "[{\"id\": 1, \"name\": \"John Doe\"}, {\"id\": 2, \"name\": \"Jane Doe\"}]"; // Exemples d'utilisateurs
    }*/

}
