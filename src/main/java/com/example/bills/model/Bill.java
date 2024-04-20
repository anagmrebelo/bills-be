package com.example.bills.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Entity
public class Bill {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
}
