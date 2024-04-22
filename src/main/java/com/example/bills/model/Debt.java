package com.example.bills.model;

import com.example.bills.model.bill.Bill;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "flatmate", "bill" }) })
public class Debt {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "flatmate")
    private Flatmate flatmate;

    @NonNull
    @ManyToOne
//    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill")
    private Bill bill;

    @NonNull
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;

    public Debt(@NonNull Flatmate flatmate, @NonNull Bill bill, @NonNull BigDecimal amount) {
        this.flatmate = flatmate;
        this.bill = bill;
        this.amount = amount;
    }
}
