package com.example.bills.controllers;

import com.example.bills.dtos.BillDto;
import com.example.bills.models.bill.Bill;
import com.example.bills.services.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BillController {
    @Autowired
    BillService billService;

    @PostMapping("/bills/{billType}")
    @ResponseStatus(HttpStatus.CREATED)
    Bill addBill(@PathVariable(name = "billType") String billType, @RequestBody @Valid BillDto billDto) {
        return billService.addBill(billType, billDto);
    }

    @DeleteMapping("/bills/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteBill(@PathVariable(name = "id") int id) {
        billService.deleteBill(id);
    }

    @GetMapping("bills/{flatId}")
    @ResponseStatus(HttpStatus.OK)
    List<Bill> getBillsByFlat(@PathVariable(name = "flatId") int flatId) {
        return billService.getBillsByFlat(flatId);
    }
}
