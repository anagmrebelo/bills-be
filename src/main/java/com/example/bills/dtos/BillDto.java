package com.example.bills.dtos;

import com.example.bills.models.Flat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@AllArgsConstructor
public class BillDto {
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;
    @Setter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "flat")
    private Flat flat;
    @NonNull
    @Column(name = "bill_month")
    private Month month;
}
