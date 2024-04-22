package com.example.bills.model;

import com.example.bills.model.utils.BillType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "bill_type")
@DiscriminatorValue("other")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "flat", "month", "bill_type" }) })
public class Bill {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "flat")
    private Flat flat;
    private Month month;
}
