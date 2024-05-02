package com.example.bills.model;

import com.example.bills.dto.FlatDto;
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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "flatmates")
    List<Flatmate> flatmateList;

    public Flat(String name) {
        this.name = name;
    }

    public Flat(FlatDto flatDto) {
        this.name = flatDto.getName();
    }

    public Flat(String name, List<Flatmate> flatmateList) {
        this.name = name;
        this.flatmateList = flatmateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return id == flat.id && Objects.equals(name, flat.name); //&& Objects.equals(flatmateList, flat.flatmateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);//, flatmateList);
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
}
