package com.example.bills.dto;

import com.example.bills.model.Flat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlatmateDto {
    @NotEmpty (message = "You must supply a flatmate name")
    private String name;
    @NotNull(message = "You must supply a flat")
    @OneToMany
    @JoinColumn(name = "flat")
    private Flat flat;
}
