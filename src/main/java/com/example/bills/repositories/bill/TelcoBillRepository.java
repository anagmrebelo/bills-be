package com.example.bills.repositories.bill;

import com.example.bills.models.bill.TelcoBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelcoBillRepository extends JpaRepository<TelcoBill, Integer> {
}
