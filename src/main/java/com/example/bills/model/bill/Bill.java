package com.example.bills.model.bill;

import com.example.bills.model.Flat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "bill_type")
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "flat", "bill_month", "bill_type" }) })
public class Bill {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @NonNull
    @Digits(integer = 6, fraction = 2) BigDecimal amount;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "flat")
    Flat flat;
    @NonNull
    @Column(name = "bill_month")
    Month month;

    public Bill(@NonNull BigDecimal amount, @NonNull Flat flat, @NonNull Month month) {
        this.amount = amount;
        this.flat = flat;
        this.month = month;
    }

    public String getType() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }
}
