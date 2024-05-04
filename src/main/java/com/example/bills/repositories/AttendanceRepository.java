package com.example.bills.repositories;

import com.example.bills.models.Attendance;
import com.example.bills.models.Flatmate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    Optional<Attendance> findByMonthAndFlatmate(Month month, Flatmate flatmate);
}
