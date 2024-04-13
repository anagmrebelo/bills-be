package com.example.bills.repository;

import com.example.bills.model.Flatmate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatmateRepository extends JpaRepository<Flatmate, Integer> {
}
