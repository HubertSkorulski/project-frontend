package com.example.finalfrontend.service;


import com.example.finalfrontend.dto.CartDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceTestSuite {

    @Autowired
    private CartService cartService;

    @Test
    void createCartTest() {
        //Given

        //When
        CartDto cartDto = cartService.createCart();
        //Then
        Assertions.assertTrue(cartDto != null);
        System.out.println(cartDto.getId());
    }

}