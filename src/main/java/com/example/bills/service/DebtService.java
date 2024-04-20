package com.example.bills.service;

import com.example.bills.model.Bill;
import com.example.bills.model.Debt;
import com.example.bills.model.Flat;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebtService {
    @Autowired
    DebtRepository debtRepository;
    @Autowired
    FlatmateService flatmateService;

    Debt addDebt(Debt debt) {
        return debtRepository.save(debt);
    }

    public List<Debt> getDebtByBillAndFlatmate(int billId, int flatmateId) {
        // TO DO: change with bill service
        Bill bill = new Bill();
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        return debtRepository.findByBillAndFlatmate(bill, flatmate);
    }

    public List<Debt> getDebtByFlatmate(int flatmateId) {
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        return debtRepository.findAllByFlatmate(flatmate);
    }
}