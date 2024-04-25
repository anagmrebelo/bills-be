package com.example.bills.model.bill;

import com.example.bills.dto.BillDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("electricity")
public class ElectricityBill extends Bill {
    public ElectricityBill(BillDto billDto) {
        this.amount = billDto.getAmount();
        this.flat = billDto.getFlat();
        this.month = billDto.getMonth();
    }
}
