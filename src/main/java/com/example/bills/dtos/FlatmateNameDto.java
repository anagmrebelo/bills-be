package com.example.bills.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlatmateNameDto {
    @NotEmpty (message = "You must supply a flatmate name")
    private String name;
}
