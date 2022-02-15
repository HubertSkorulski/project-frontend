package com.example.finalfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long cartId;
    private Long customerId;
}
