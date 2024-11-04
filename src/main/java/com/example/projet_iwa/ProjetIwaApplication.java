package com.example.projet_iwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetIwaApplication {

	public static void main(String[] args) {
		System.out.println("Lancement de l'application Projet IWA...");
		SpringApplication.run(ProjetIwaApplication.class, args);
		System.out.println("Application Projet IWA démarrée avec succès !");
	}

}
