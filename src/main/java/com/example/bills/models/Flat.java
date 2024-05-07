package com.example.bills.models;

import com.example.bills.dtos.FlatDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Flat {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @NotEmpty(message = "You must supply a flat name")
    private String name;

    @Nullable
    @OneToMany
    @JoinColumn(name = "flatmates")
    private List<Flatmate> flatmateList;

    private boolean closed;

    public Flat(String name) {
        this.name = name;
        this.closed = false;
    }

    public Flat(FlatDto flatDto) {
        this.name = flatDto.getName();
        this.closed = false;
    }

    public Flat(String name, List<Flatmate> flatmateList) {
        this.name = name;
        this.flatmateList = flatmateList;
        closed = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return id == flat.id && closed == flat.closed && Objects.equals(name, flat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, closed);
    }

    // Methods
    public void addFlatmate(Flatmate flatmate) {
        if (flatmateList == null) {
            flatmateList = new ArrayList<>();
            flatmateList.add(flatmate);
        } else {
            flatmateList.add(flatmate);
        }
    }

    public void closeFlat() {
        closed = true;
    }
}
