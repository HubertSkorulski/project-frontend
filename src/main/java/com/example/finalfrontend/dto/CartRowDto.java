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

    /*public CartRow(DishDto dishDto, int quantity) {
        this.dishDto = dishDto;
        this.quantity = quantity;
        this.price = quantity * dishDto.getPrice();
    }*/
}
