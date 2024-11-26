package com.projet_iwa.api_gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permettre les requêtes depuis toutes les origines pour tous les chemins
        registry.addMapping("/**")
                .allowedOrigins("*") // Autoriser toutes les origines
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Autoriser certaines méthodes
                .allowedHeaders("*"); // Autoriser tous les headers
    }
}
