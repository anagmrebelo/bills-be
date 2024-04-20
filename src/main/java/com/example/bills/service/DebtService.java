package com.example.bills.service;

import com.example.bills.model.Debt;
import com.example.bills.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebtService {
    @Autowired
    DebtRepository debtRepository;

    Debt addDebt(Debt debt) {
        return debtRepository.save(debt);
    }
}
