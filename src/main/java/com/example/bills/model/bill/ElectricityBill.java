package com.example.bills.model.bill;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("electricity")
public class ElectricityBill extends Bill {
}
