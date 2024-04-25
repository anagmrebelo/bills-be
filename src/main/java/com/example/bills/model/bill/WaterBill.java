package com.example.bills.model.bill;

import com.example.bills.dto.BillDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("water")
public class WaterBill extends Bill {
    public WaterBill(BillDto billDto) {
        this.amount = billDto.getAmount();
        this.flat = billDto.getFlat();
        this.month = billDto.getMonth();
    }
}
