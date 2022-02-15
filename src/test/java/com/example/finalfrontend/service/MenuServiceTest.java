package com.example.finalfrontend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.awt.*;

@SpringBootTest
class MenuServiceTest {

    @Autowired
    private DishService dishService;
    @Autowired
    private MenuService menuService;

    @Test
    void prepareMenuTest() {
        //Given

        //When
        menuService.prepareMenu();
        //Then
        int dishes = dishService.getDishes().length;
        Assertions.assertEquals(12,dishes);

    }


}