package com.example.bills.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class Flatmate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Setter
    @NotEmpty
    private String name;
    @Setter
    @NotNull
    @ManyToOne
    @JoinColumn(name = "flat")
    private Flat flat;

    public Flatmate(String name, Flat flat) {
        this.name = name;
        this.flat = flat;
    }
}
