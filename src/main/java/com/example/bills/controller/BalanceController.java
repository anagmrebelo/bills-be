package com.example.bills.controller;

import com.example.bills.model.Balance;
import com.example.bills.model.Flatmate;
import com.example.bills.service.DebtService;
import com.example.bills.service.FlatmateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BalanceController {
    @Autowired
    FlatmateService flatmateService;
    @Autowired
    DebtService debtService;
    @GetMapping("/balance/{flatmateId}")
    BigDecimal getBalance(@PathVariable(name = "flatmateId") int flatmateId) {
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        Balance balance = new Balance(flatmate, debtService);

        return balance.getBalance();
    }
}
