package com.example.bills.controller;

import com.example.bills.service.DebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebtController {
    @Autowired
    DebtService debtService;
}
