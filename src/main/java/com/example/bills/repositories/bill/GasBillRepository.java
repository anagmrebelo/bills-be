package com.example.bills.repositories.bill;

import com.example.bills.models.bill.GasBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasBillRepository extends JpaRepository<GasBill, Integer> {
}
