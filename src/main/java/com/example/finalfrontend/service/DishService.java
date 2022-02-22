package com.example.finalfrontend.service;

import com.example.finalfrontend.dto.DishDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


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

    public List<DishDto> getFilteredByTitleDishDtoList(String name) {
        return Arrays.stream(getDishes())
                .filter(dishDto -> dishDto.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public DishDto updateDish(Long dishId,String name, double price, Long groupId) {
        return this.webClient
                .put()
                .uri(dishId + "/" + name + "/" + price + "/" + groupId)
                .retrieve()
                .bodyToMono(DishDto.class)
                .block();
    }

    public DishDto createDish(String name, double price, Long groupId) {
        return this.webClient
                .post()
                .uri(name + "/" + price + "/" + groupId)
                .retrieve()
                .bodyToMono(DishDto.class)
                .block();
    }

    public void deleteDish(Long id) {
        this.webClient
                .delete()
                .uri(String.valueOf(id))

                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
