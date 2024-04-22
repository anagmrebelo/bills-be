package com.example.bills.dto;

import com.example.bills.model.Flat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Month;

@Getter
public class BillDto {
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "flat")
    private Flat flat;
    @NonNull
    @Column(name = "bill_month")
    private Month month;
}
