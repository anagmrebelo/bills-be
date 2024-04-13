package com.example.bills.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlatDto {
    @NotEmpty(message = "You must supply a flat name")
    private String name;
}