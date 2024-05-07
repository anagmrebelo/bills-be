package com.example.bills.models;

import com.example.bills.dtos.FlatDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private boolean closed;

    public Flat(String name) {
        this.name = name;
        this.closed = false;
    }

    public Flat(FlatDto flatDto) {
        this.name = flatDto.getName();
        this.closed = false;
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

    public void closeFlat() {
        closed = true;
    }
}
