package com.example.bills.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Month;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "flatmate", "attendance_month" }) })
public class Attendance {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @Setter
    @ManyToOne
    @NonNull
    @JoinColumn(name = "flatmate")
    private Flatmate flatmate;

    @NonNull
    @Column(name = "attendance_month")
    private Month month;

    @NonNull
    private Boolean wasPresent;

    public Attendance(@NonNull Flatmate flatmate, @NonNull Month month, @NonNull Boolean wasPresent) {
        this.flatmate = flatmate;
        this.month = month;
        this.wasPresent = wasPresent;
    }
}
