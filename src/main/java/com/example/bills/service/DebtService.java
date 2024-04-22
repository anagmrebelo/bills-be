package com.example.bills.service;

import com.example.bills.model.bill.Bill;
import com.example.bills.model.Debt;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.bill.BillRepository;
import com.example.bills.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebtService {
    @Autowired
    DebtRepository debtRepository;
    @Autowired
    FlatmateService flatmateService;
    @Autowired
    BillRepository billRepository;

    Debt addDebt(Debt debt) {
        //TO DO call bill service
        flatmateService.getFlatmate(debt.getFlatmate().getId());
        return debtRepository.save(debt);
    }

    public List<Debt> getDebtByBillAndFlatmate(int billId, int flatmateId) {
        // TO DO: change with bill service
        Bill bill = billRepository.findById(billId).get();
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        return debtRepository.findByBillAndFlatmate(bill, flatmate);
    }

    public List<Debt> getDebtByFlatmate(int flatmateId) {
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        return debtRepository.findAllByFlatmate(flatmate);
    }
}
