package com.example.bills.service;

import com.example.bills.model.bill.Bill;
import com.example.bills.model.Debt;
import com.example.bills.model.Flatmate;
import com.example.bills.repository.DebtRepository;
import com.example.bills.repository.bill.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DebtService {
    @Autowired
    DebtRepository debtRepository;
    @Autowired
    FlatmateService flatmateService;
    @Autowired
    BillRepository billRepository;

    Debt addDebt(Debt debt) {
        flatmateService.getFlatmate(debt.getFlatmate().getId());
        return debtRepository.save(debt);
    }

    public List<Debt> getDebtByBillAndFlatmate(int billId, int flatmateId) {
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found"));
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        return debtRepository.findByBillAndFlatmate(bill, flatmate);
    }

    public List<Debt> getDebtByFlatmate(int flatmateId) {
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        return debtRepository.findAllByFlatmate(flatmate);
    }

    public void deleteByBill(Bill bill) {
        debtRepository.deleteAllByBill(bill);
    }
}
