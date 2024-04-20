package com.example.bills.controller;

import com.example.bills.model.Debt;
import com.example.bills.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class DebtController {
    @Autowired
    DebtService debtService;

    @GetMapping("/debts")
    List<Debt> getDebts(@RequestParam(name = "flatmate") int flatmateId, @RequestParam(name = "bill") Optional<Integer> billId) {
        if (billId.isPresent()) {
            return debtService.getDebtByBillAndFlatmate(billId.get(), flatmateId);
        }
        return debtService.getDebtByFlatmate(flatmateId);
    }
}
