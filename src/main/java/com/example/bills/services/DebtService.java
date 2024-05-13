package com.example.bills.services;

import com.example.bills.models.bill.Bill;
import com.example.bills.models.Debt;
import com.example.bills.models.Flatmate;
import com.example.bills.repositories.DebtRepository;
import com.example.bills.repositories.bill.BillRepository;
import com.example.bills.security.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        flatmateService.getFlatmate(debt.getFlatmate().getId());
        return debtRepository.save(debt);
    }

    public List<Debt> getDebtByBillAndFlatmate(int billId, int flatmateId, User user) {
        List<Debt> debts = getDebtByBillAndFlatmate(billId, flatmateId);

        if (user.getFlat() == null || user.getFlat().getId() != flatmateService.getFlatmate(flatmateId).getFlat().getId() || user.getFlat().getId() != billRepository.findById(billId).get().getFlat().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only access to your flat information");
        }

        return debts;
    }

    public List<Debt> getDebtByBillAndFlatmate(int billId, int flatmateId) {
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found"));
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        return debtRepository.findByBillAndFlatmate(bill, flatmate);
    }

    public List<Debt> getDebtByFlatmate(int flatmateId, User user) {
        List<Debt> debts = getDebtByFlatmate(flatmateId);

        if (user.getFlat() == null || user.getFlat().getId() != flatmateService.getFlatmate(flatmateId).getFlat().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only access to your flat information");
        }

        return debts;
    }

    public List<Debt> getDebtByFlatmate(int flatmateId) {
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        return debtRepository.findAllByFlatmate(flatmate);
    }

    public void deleteByBill(Bill bill) {
        debtRepository.deleteAllByBill(bill);
    }
}
