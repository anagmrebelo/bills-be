package com.example.bills.model.bill;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("telco")
public class TelcoBill extends Bill {
}
