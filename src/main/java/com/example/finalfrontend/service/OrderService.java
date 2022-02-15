package com.example.finalfrontend.service;

import com.example.finalfrontend.dto.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderService {

    private final WebClient webClient;

    public OrderService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/v1/order/").build();
    }

    public OrderDto createOrder(Long cartId, Long customerId) {
        return this.webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("cartId",cartId)
                    .queryParam("customerId",customerId)
                    .build())
                .retrieve()
                .bodyToMono(OrderDto.class)
                .block();
    }

}
