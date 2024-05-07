package com.example.bills.repositories;

import com.example.bills.models.Flatmate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatmateRepository extends JpaRepository<Flatmate, Integer> {
    List<Flatmate> findAllByFlatId(int id);
}
