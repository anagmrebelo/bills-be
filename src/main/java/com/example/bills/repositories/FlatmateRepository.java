package com.example.bills.repositories;

import com.example.bills.models.Flatmate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatmateRepository extends JpaRepository<Flatmate, Integer> {
}
