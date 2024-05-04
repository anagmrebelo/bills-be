package com.example.bills.controllers;

import com.example.bills.models.Balance;
import com.example.bills.models.Flatmate;
import com.example.bills.services.DebtService;
import com.example.bills.services.FlatmateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class BalanceController {
    @Autowired
    FlatmateService flatmateService;
    @Autowired
    DebtService debtService;
    @GetMapping("/balance/{flatmateId}")
    @ResponseStatus(HttpStatus.OK)
    BigDecimal getBalance(@PathVariable(name = "flatmateId") int flatmateId) {
        Flatmate flatmate = flatmateService.getFlatmate(flatmateId);
        Balance balance = new Balance(flatmate, debtService);

        return balance.getBalance();
    }
}
