package com.example.finalfrontend.service;

import com.example.finalfrontend.dto.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CustomerService {

    private final WebClient webClient;

    public CustomerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/v1/customer/").build();
    }

    public CustomerDto createCustomer(String name, String surname, String emailAddress) {
        return this.webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("name", name)
                        .queryParam("surname", surname)
                        .queryParam("emailAddress",emailAddress)
                        .build())
                .retrieve()
                .bodyToMono(CustomerDto.class)
                .block();
    }

    public CustomerDto[] getAllCustomers() {
        return this.webClient.get()
                .uri("getCustomers")
                .retrieve()
                .bodyToMono(CustomerDto[].class)
                .block();
    }
}
