package com.example.bills.controllers;

import com.example.bills.dtos.BillDto;
import com.example.bills.models.bill.Bill;
import com.example.bills.security.models.User;
import com.example.bills.security.services.UserService;
import com.example.bills.services.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BillController {
    @Autowired
    BillService billService;
    @Autowired
    UserService userService;

    @PostMapping("/bills/{billType}")
    @ResponseStatus(HttpStatus.CREATED)
    Bill addBill(@PathVariable(name = "billType") String billType, @RequestBody @Valid BillDto billDto) {
        User user = fetchUser();
        return billService.addBill(billType, billDto, user);
    }

    @DeleteMapping("/bills/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteBill(@PathVariable(name = "id") int id) {
        User user = fetchUser();
        billService.deleteBill(id, user);
    }

    @GetMapping("bills/{flatId}")
    @ResponseStatus(HttpStatus.OK)
    List<Bill> getBillsByFlat(@PathVariable(name = "flatId") int flatId) {
        User user = fetchUser();
        return billService.getBillsByFlat(flatId, user);
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
