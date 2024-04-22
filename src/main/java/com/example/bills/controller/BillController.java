package com.example.bills.controller;

import com.example.bills.dto.BillDto;
import com.example.bills.model.bill.Bill;
import com.example.bills.service.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
