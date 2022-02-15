package com.example.finalfrontend.service;

import com.example.finalfrontend.dto.DishDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class DishService {

    private final WebClient webClient;

    public DishService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/v1/dish/").build();
    }

    public DishDto[] getDishes() {
        return this.webClient
                .get()
                .uri("getDishes")
                .retrieve()
                .bodyToMono(DishDto[].class)
                .block();
    }
}
