package com.example.finalfrontend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConfirmationService {

    private final WebClient webClient;

    public ConfirmationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/v1/confirmation/").build();
    }

    public String sentConfirmation(Long orderId) {
        return this.webClient
                .get()
                .uri("/" + orderId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
