package com.projet_iwa.api_gateway;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayApplicationTests {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080")
                .responseTimeout(Duration.ofSeconds(180))
                .build();
    }

    @Test
    void sendRequestToDebugService() {
        webTestClient.get().uri("/debug/test-endpoint")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Expected response from debug service");
    }
}
