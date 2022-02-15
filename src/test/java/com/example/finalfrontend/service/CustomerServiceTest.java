package com.example.finalfrontend.service;

import com.example.finalfrontend.dto.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomerTest() {
        //Given

        //When
        CustomerDto customerDto = customerService.
                createCustomer("Adam","Malysz","adam@malysz");
        //Then
        int customers = customerService.getAllCustomers().length;
        Assertions.assertEquals(1,customers);
    }
}