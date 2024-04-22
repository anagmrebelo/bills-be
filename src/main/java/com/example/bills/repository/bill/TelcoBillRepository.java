package com.example.bills.repository.bill;

import com.example.bills.model.bill.TelcoBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelcoBillRepository extends JpaRepository<TelcoBill, Integer> {
}
