package com.example.bills.repository.bill;

import com.example.bills.model.bill.GasBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasBillRepository extends JpaRepository<GasBill, Integer> {
}
