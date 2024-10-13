package com.revature.order.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class UserClientService {

    private final WebClient webClient;

    public UserClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build(); // Base URL to service-user
    }

    public Mono<String> getUserById(String userId) {
        return webClient.get()
                .uri("/api/users/{id}", userId) // User service endpoint
                .retrieve()
                .bodyToMono(String.class); // Returning user info as a String
    }
}
