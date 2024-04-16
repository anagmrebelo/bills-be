package com.example.bills.model;

import com.example.bills.dto.FlatmateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Flatmate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Setter
    @NotEmpty
    private String name;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "flat")
    private Flat flat;

    public Flatmate(String name, Flat flat) {
        this.name = name;
        this.flat = flat;
    }

    public Flatmate(FlatmateDto flatmateDto) {
        this.name = flatmateDto.getName();
        this.flat = flatmateDto.getFlat();
    }

}
