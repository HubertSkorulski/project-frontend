package com.example.finalfrontend.service;

import com.example.finalfrontend.dto.GroupDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GroupService {

    private final WebClient webClient;

    public GroupService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/v1/group/").build();
    }

    public GroupDto[] getGroups() {
        return this.webClient
                .get()
                .retrieve()
                .bodyToMono(GroupDto[].class)
                .block();
    }


    public GroupDto getGroup(Long groupId) {
        return this.webClient
                .get()
                .uri(String.valueOf(groupId))
                .retrieve()
                .bodyToMono(GroupDto.class)
                .block();
    }
}
