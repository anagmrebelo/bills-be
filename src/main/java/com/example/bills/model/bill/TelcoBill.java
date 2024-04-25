package com.example.bills.model.bill;

import com.example.bills.dto.BillDto;
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
