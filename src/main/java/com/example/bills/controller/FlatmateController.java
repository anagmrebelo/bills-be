package com.example.bills.controller;

import com.example.bills.model.Flatmate;
import com.example.bills.service.FlatmateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlatmateController {
    @Autowired
    FlatmateService flatmateService;

    @GetMapping("/flatmates")
    @ResponseStatus(HttpStatus.OK)
    List<Flatmate> getAllFlatmates() {
        return flatmateService.getAllFlatmates();
    }
}
