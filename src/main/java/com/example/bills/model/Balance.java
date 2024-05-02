package com.example.bills.model;

import com.example.bills.service.DebtService;

import java.math.BigDecimal;
import java.util.List;

public class Balance {
    private Flatmate flatmate;
    private DebtService debtService;

    public Balance(Flatmate flatmate, DebtService debtService) {
        this.flatmate = flatmate;
        this.debtService = debtService;
    }

    public BigDecimal getBalance() {
       // List<Debt> debts = debtService.getDebtByFlatmate(this.flatmate.getId());
        BigDecimal totalDebt = new BigDecimal("0");
      /*  for (Debt debt : debts) {
            totalDebt = totalDebt.add(debt.getAmount());
        }*/

        return totalDebt;
    }
}
