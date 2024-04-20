package com.example.bills.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Entity
public class Debt {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @NonNull
    @ManyToOne
    private Flatmate flatmate;

    @NonNull
    @ManyToOne
    private Bill bill;

    @NonNull
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;
}
