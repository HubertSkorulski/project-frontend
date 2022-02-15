package com.example.finalfrontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String name;
    private String surname;
    private String emailAddress;
}
