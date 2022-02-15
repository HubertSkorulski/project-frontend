package com.example.finalfrontend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CartDto {

    @JsonProperty("id")
    private Long id;
}
