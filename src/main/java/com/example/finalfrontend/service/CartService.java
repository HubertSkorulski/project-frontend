package com.example.finalfrontend.service;

import com.example.finalfrontend.dto.CartDto;
import com.example.finalfrontend.dto.CartRowDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class CartService {

    private final WebClient webClient;

    public CartService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/v1/cart/").build();
    }

    public CartDto addDish(String dishName, Long cartId, int quantity) {
        return this.webClient
                .put()
                .uri("addProduct/" + dishName + "/" + cartId + "/" + quantity)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public CartDto removeDish(String dishName, Long cartId) {
        return this.webClient
                .put()
                .uri("removeDish/" + cartId + "/" + dishName)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();

    }

    public CartRowDto[] getCartRows(Long cartId) {
        return this.webClient
                .get()
                .uri("summary" +"/" + cartId)
                .retrieve()
                .bodyToMono(CartRowDto[].class)
                .block();
    }

    public CartDto createCart() {
        return this.webClient
                .get()
                .uri("create")
                .retrieve()
                .toEntity(CartDto.class)
                .block().getBody();
    }

    public double getTotalCost(Long cartId) {
        return this.webClient
                .get()
                .uri("totalCost/" + cartId )
                .retrieve()
                .toEntity(Double.class)
                .block().getBody();
    }
}
