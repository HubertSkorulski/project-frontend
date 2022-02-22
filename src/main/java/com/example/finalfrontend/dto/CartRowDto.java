package com.example.finalfrontend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CartRowDto {

    @JsonProperty("dishName")
    private String dishName;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private double price;

    @JsonProperty("unitPrice")
    private double unitPrice;
}
