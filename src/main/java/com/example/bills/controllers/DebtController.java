package com.example.bills.controllers;

import com.example.bills.models.Debt;
import com.example.bills.security.models.User;
import com.example.bills.security.services.UserService;
import com.example.bills.services.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class DebtController {
    @Autowired
    DebtService debtService;
    @Autowired
    UserService userService;

    @GetMapping("/debts")
    List<Debt> getDebts(@RequestParam(name = "flatmate") int flatmateId, @RequestParam(name = "bill") Optional<Integer> billId) {
        User user = fetchUser();
        if (billId.isPresent()) {
            return debtService.getDebtByBillAndFlatmate(billId.get(), flatmateId, user);
        }
        return debtService.getDebtByFlatmate(flatmateId, user);
    }

    User fetchUser() {
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            user = userService.getUser(userDetails.getUsername());
        } else {
            user = userService.getUser((String) principal);
        }
        return user;
    }
}
