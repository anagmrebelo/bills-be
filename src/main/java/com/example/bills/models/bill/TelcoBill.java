package com.example.bills.models.bill;

import com.example.bills.dtos.BillDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("telco")
public class TelcoBill extends Bill {
    public TelcoBill(BillDto billDto) {
        this.amount = billDto.getAmount();
        this.flat = billDto.getFlat();
        this.month = billDto.getMonth();
    }
}
