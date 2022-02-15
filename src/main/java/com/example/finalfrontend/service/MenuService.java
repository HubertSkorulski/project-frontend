package com.example.finalfrontend.service;

import com.example.finalfrontend.dto.DishDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MenuService {

    private final WebClient webClient;

    public MenuService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/v1/menu/").build();
    }

    public DishDto[] prepareMenu() {

        return this.webClient
                .get()
                .uri("")
                .retrieve()
                .bodyToMono(DishDto[].class)
                .block();
    }
}
