package com.example.bills.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
public class Flat {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    @NotEmpty(message = "You must supply a flat name")
    private String name;
    @OneToMany
    @JoinColumn(name = "flatmates")
    List<Flatmate> flatmateList;
}
