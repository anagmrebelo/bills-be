package com.example.bills.model.bill;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("gas")
public class GasBill extends Bill {
}
